package org.delivery.api.menu.model;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

@Data
public class MenuUpdateRequest {
    @Size(max = 200)
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(0)
    private Integer stock;
}
