package org.delivery.api.restaurant;

import lombok.RequiredArgsConstructor;
import org.delivery.api.restaurant.model.RestaurantCreateRequest;
import org.delivery.api.restaurant.model.RestaurantResponse;
import org.delivery.api.restaurant.model.RestaurantUpdateRequest;
import org.delivery.db.restaurant.RestaurantEntity;
import org.delivery.db.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResponse create(RestaurantCreateRequest request) {
        var entity = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .build();
        var saved = restaurantRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public RestaurantResponse get(Long id) {
        var entity = restaurantRepository.findById(id).orElseThrow();
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<RestaurantResponse> list() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantResponse update(Long id, RestaurantUpdateRequest request) {
        var entity = restaurantRepository.findById(id).orElseThrow();
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        return toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

    private RestaurantResponse toResponse(RestaurantEntity entity) {
        return RestaurantResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }
}


