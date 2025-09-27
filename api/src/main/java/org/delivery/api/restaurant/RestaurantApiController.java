package org.delivery.api.restaurant;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.restaurant.model.RestaurantCreateRequest;
import org.delivery.api.restaurant.model.RestaurantResponse;
import org.delivery.api.restaurant.model.RestaurantUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Api<RestaurantResponse> create(@RequestBody @Valid RestaurantCreateRequest request) {
        return Api.OK(restaurantService.create(request));
    }

    @GetMapping("/{id}")
    public Api<RestaurantResponse> get(@PathVariable Long id) {
        return Api.OK(restaurantService.get(id));
    }

    @GetMapping
    public Api<List<RestaurantResponse>> list() {
        return Api.OK(restaurantService.list());
    }

    @PutMapping("/{id}")
    public Api<RestaurantResponse> update(@PathVariable Long id, @RequestBody @Valid RestaurantUpdateRequest request) {
        return Api.OK(restaurantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }
}


