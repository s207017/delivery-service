package org.delivery.api.order;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.order.model.OrderCreateRequest;
import org.delivery.api.order.model.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Api<OrderResponse> create(@RequestBody @Valid OrderCreateRequest request) {
        return Api.OK(orderService.create(request));
    }

    @GetMapping("/{id}")
    public Api<OrderResponse> get(@PathVariable Long id) {
        return Api.OK(orderService.get(id));
    }
}


