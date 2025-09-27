package org.delivery.api.menu;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.menu.model.MenuCreateRequest;
import org.delivery.api.menu.model.MenuResponse;
import org.delivery.api.menu.model.MenuUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Api<MenuResponse> create(@RequestBody @Valid MenuCreateRequest request) {
        return Api.OK(menuService.create(request));
    }

    @GetMapping("/{id}")
    public Api<MenuResponse> get(@PathVariable Long id) {
        return Api.OK(menuService.get(id));
    }

    @GetMapping
    public Api<List<MenuResponse>> list() {
        return Api.OK(menuService.list());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public Api<List<MenuResponse>> listByRestaurant(@PathVariable Long restaurantId) {
        return Api.OK(menuService.listByRestaurant(restaurantId));
    }

    @PutMapping("/{id}")
    public Api<MenuResponse> update(@PathVariable Long id, @RequestBody @Valid MenuUpdateRequest request) {
        return Api.OK(menuService.update(id, request));
    }

    @PutMapping("/{id}/stock")
    public Api<MenuResponse> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        return Api.OK(menuService.updateStock(id, stock));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
