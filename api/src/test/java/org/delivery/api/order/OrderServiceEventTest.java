package org.delivery.api.order;

import org.delivery.api.ApiApplication;
import org.delivery.api.order.event.OrderCreatedEvent;
import org.delivery.api.order.model.OrderCreateRequest;
import org.delivery.db.menu.MenuEntity;
import org.delivery.db.menu.MenuRepository;
import org.delivery.db.order.OrderEntity;
import org.delivery.db.order.OrderRepository;
import org.delivery.db.restaurant.RestaurantEntity;
import org.delivery.db.restaurant.RestaurantRepository;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ApiApplication.class, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
@RecordApplicationEvents
class OrderServiceEventTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private MenuRepository menuRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void create_publishesOrderCreatedEvent() {
        var user = new UserEntity();
        user.setId(1L);

        var restaurant = RestaurantEntity.builder()
                .name("R1")
                .address("A1")
                .build();
        restaurant.setId(2L);

        var menu = MenuEntity.builder()
                .restaurant(restaurant)
                .name("M1")
                .price(new BigDecimal("9.99"))
                .stock(10)
                .build();
        menu.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));
        when(menuRepository.findById(10L)).thenReturn(Optional.of(menu));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(inv -> {
            var order = (OrderEntity) inv.getArgument(0);
            order.setId(100L);
            return order;
        });

        var req = new OrderCreateRequest();
        req.setAccountId(1L);
        req.setRestaurantId(2L);
        var item = new OrderCreateRequest.Item();
        item.setMenuId(10L);
        item.setQuantity(2);
        req.setItems(List.of(item));

        orderService.create(req);

        var count = applicationEvents.stream(OrderCreatedEvent.class).count();
        assertThat(count).isEqualTo(1);
    }
}


