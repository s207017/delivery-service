package org.delivery.api.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.order.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    @Async
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Processing order asynchronously: {}", event.getOrderId());
        // TODO: payment authorization or dispatch matching can be triggered here
    }
}


