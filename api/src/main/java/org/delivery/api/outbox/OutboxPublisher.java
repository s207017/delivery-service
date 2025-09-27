package org.delivery.api.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.order.event.OrderCreatedEvent;
import org.delivery.db.outbox.OutboxEventEntity;
import org.delivery.db.outbox.OutboxEventRepository;
import org.delivery.db.outbox.OutboxStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObjectMapper objectMapper;

    @Transactional
    @Scheduled(fixedDelay = 2000)
    public void publishPending() {
        var pending = outboxEventRepository.findTop50ByStatusOrderByIdAsc(OutboxStatus.PENDING);
        for (OutboxEventEntity event : pending) {
            try {
                if ("OrderCreated".equals(event.getEventType())) {
                    var payload = objectMapper.readTree(event.getPayload());
                    var orderId = payload.get("orderId").asLong();
                    applicationEventPublisher.publishEvent(new OrderCreatedEvent(orderId));
                }
                event.setStatus(OutboxStatus.SENT);
                event.setSentAt(LocalDateTime.now());
            } catch (Exception e) {
                log.error("Failed to publish outbox event id={}", event.getId(), e);
                event.setStatus(OutboxStatus.FAILED);
            }
        }
    }
}


