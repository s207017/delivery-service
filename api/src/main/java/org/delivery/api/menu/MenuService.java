package org.delivery.api.menu;

import lombok.RequiredArgsConstructor;
import org.delivery.api.menu.model.MenuCreateRequest;
import org.delivery.api.menu.model.MenuResponse;
import org.delivery.api.menu.model.MenuUpdateRequest;
import org.delivery.db.menu.MenuEntity;
import org.delivery.db.menu.MenuRepository;
import org.delivery.db.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public MenuResponse create(MenuCreateRequest request) {
        var restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        var entity = MenuEntity.builder()
                .restaurant(restaurant)
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        var saved = menuRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MenuResponse get(Long id) {
        var entity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> list() {
        return menuRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> listByRestaurant(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuResponse update(Long id, MenuUpdateRequest request) {
        var entity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getPrice() != null) {
            entity.setPrice(request.getPrice());
        }
        if (request.getStock() != null) {
            entity.setStock(request.getStock());
        }

        var saved = menuRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu not found");
        }
        menuRepository.deleteById(id);
    }

    @Transactional
    public MenuResponse updateStock(Long id, Integer newStock) {
        var entity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        if (newStock < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }

        entity.setStock(newStock);
        var saved = menuRepository.save(entity);
        return toResponse(saved);
    }

    private MenuResponse toResponse(MenuEntity entity) {
        return MenuResponse.builder()
                .id(entity.getId())
                .restaurantId(entity.getRestaurant().getId())
                .restaurantName(entity.getRestaurant().getName())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .version(entity.getVersion())
                .build();
    }
}
