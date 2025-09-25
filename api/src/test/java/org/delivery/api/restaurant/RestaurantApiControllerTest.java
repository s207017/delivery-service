package org.delivery.api.restaurant;

import org.delivery.api.restaurant.model.RestaurantCreateRequest;
import org.delivery.api.restaurant.model.RestaurantResponse;
import org.delivery.api.restaurant.model.RestaurantUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantApiController.class)
class RestaurantApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void create_returnsCreatedRestaurant() throws Exception {
        var requestJson = "{\"name\":\"R1\",\"address\":\"A1\"}";
        var response = RestaurantResponse.builder().id(1L).name("R1").address("A1").build();
        given(restaurantService.create(org.mockito.ArgumentMatchers.any(RestaurantCreateRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body.id").value(1))
                .andExpect(jsonPath("$.body.name").value("R1"))
                .andExpect(jsonPath("$.result.resultCode").value(200));
    }

    @Test
    void get_returnsRestaurant() throws Exception {
        var response = RestaurantResponse.builder().id(2L).name("R2").address("A2").build();
        given(restaurantService.get(2L)).willReturn(response);

        mockMvc.perform(get("/api/restaurants/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.id").value(2))
                .andExpect(jsonPath("$.body.name").value("R2"));
    }

    @Test
    void list_returnsRestaurants() throws Exception {
        var resp = List.of(
                RestaurantResponse.builder().id(1L).name("R1").address("A1").build(),
                RestaurantResponse.builder().id(2L).name("R2").address("A2").build()
        );
        given(restaurantService.list()).willReturn(resp);

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body[0].id").value(1))
                .andExpect(jsonPath("$.body[1].id").value(2));
    }

    @Test
    void update_returnsUpdatedRestaurant() throws Exception {
        var requestJson = "{\"name\":\"R1-upd\",\"address\":\"A1-upd\"}";
        var response = RestaurantResponse.builder().id(1L).name("R1-upd").address("A1-upd").build();
        given(restaurantService.update(org.mockito.ArgumentMatchers.eq(1L), org.mockito.ArgumentMatchers.any(RestaurantUpdateRequest.class))).willReturn(response);

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.name").value("R1-upd"));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isNoContent());
    }
}


