package org.delivery.api.menu.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MenuResponse {
    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long version;
}
