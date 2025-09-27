package org.delivery.api.order;

import lombok.RequiredArgsConstructor;
import org.delivery.api.order.event.OrderCreatedEvent;
import org.delivery.api.order.model.OrderCreateRequest;
import org.delivery.api.order.model.OrderResponse;
import org.delivery.db.menu.MenuEntity;
import org.delivery.db.menu.MenuRepository;
import org.delivery.db.order.OrderEntity;
import org.delivery.db.order.OrderItemEntity;
import org.delivery.db.order.OrderRepository;
import org.delivery.db.order.OrderStatus;
import org.delivery.db.restaurant.RestaurantRepository;
import org.delivery.db.outbox.OutboxEventEntity;
import org.delivery.db.outbox.OutboxEventRepository;
import org.delivery.db.outbox.OutboxStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.delivery.db.user.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100))
    public OrderResponse create(OrderCreateRequest request) {
        // Get authenticated user from security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        
        var restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow();

        var order = OrderEntity.builder()
                .account(user)
                .restaurant(restaurant)
                .status(OrderStatus.CREATED)
                .orderedAt(LocalDateTime.now())
                .build();

        var items = request.getItems().stream().map(i -> {
            var menu = menuRepository.findById(i.getMenuId()).orElseThrow();
            decrementStockWithOptimisticLock(menu, i.getQuantity());
            return buildOrderItem(order, menu, i.getQuantity(), menu.getPrice());
        }).collect(Collectors.toList());

        order.setItems(items);
        var saved = orderRepository.save(order);

        // Write to outbox for reliable asynchronous processing
        writeOutbox("OrderCreated", saved.getId());

        // Backward-compatible in-process event (can be removed once outbox publisher is relied upon)
        eventPublisher.publishEvent(new OrderCreatedEvent(saved.getId()));

        return toResponse(saved);
    }

    private void writeOutbox(String eventType, Long orderId) {
        try {
            var payload = objectMapper.createObjectNode().put("orderId", orderId).toString();
            var outbox = OutboxEventEntity.builder()
                    .eventType(eventType)
                    .payload(payload)
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
            outboxEventRepository.save(outbox);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to write outbox event", e);
        }
    }
    @Transactional(readOnly = true)
    public OrderResponse get(Long id) {
        var order = orderRepository.findById(id).orElseThrow();
        return toResponse(order);
    }

    private void decrementStockWithOptimisticLock(MenuEntity menu, int qty) {
        var remaining = menu.getStock() - qty;
        if (remaining < 0) {
            throw new IllegalStateException("Insufficient stock");
        }
        menu.setStock(remaining);
    }

    private OrderItemEntity buildOrderItem(OrderEntity order, MenuEntity menu, int quantity, BigDecimal unitPrice) {
        var item = OrderItemEntity.builder()
                .order(order)
                .menu(menu)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
        return item;
    }

    private OrderResponse toResponse(OrderEntity entity) {
        List<OrderResponse.OrderResponseItem> items = entity.getItems() == null ? List.of() : entity.getItems().stream()
                .map(i -> OrderResponse.OrderResponseItem.builder()
                        .menuId(i.getMenu().getId())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice().toPlainString())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(entity.getId())
                .accountId(entity.getAccount().getId())
                .restaurantId(entity.getRestaurant().getId())
                .status(entity.getStatus().name())
                .orderedAt(entity.getOrderedAt())
                .items(items)
                .build();
    }
}


