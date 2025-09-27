package org.delivery.api.restaurant.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RestaurantUpdateRequest {
    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String address;
}


