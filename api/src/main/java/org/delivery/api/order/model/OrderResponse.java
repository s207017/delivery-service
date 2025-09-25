package org.delivery.api.order.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long accountId;
    private Long restaurantId;
    private String status;
    private LocalDateTime orderedAt;
    private List<OrderResponseItem> items;

    @Data
    @Builder
    public static class OrderResponseItem {
        private Long menuId;
        private Integer quantity;
        private String unitPrice;
    }
}


