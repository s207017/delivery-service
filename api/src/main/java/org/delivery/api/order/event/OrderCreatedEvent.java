package org.delivery.api.order.event;

import lombok.Value;

@Value
public class OrderCreatedEvent {
    Long orderId;
}


