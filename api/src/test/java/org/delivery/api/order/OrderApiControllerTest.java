package org.delivery.api.order;

import org.delivery.api.order.model.OrderCreateRequest;
import org.delivery.api.order.model.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderApiController.class)
class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void create_returnsCreatedOrder() throws Exception {
        var requestJson = "{\"accountId\":1,\"restaurantId\":2,\"items\":[{\"menuId\":10,\"quantity\":2}]}";
        var response = OrderResponse.builder()
                .id(100L)
                .accountId(1L)
                .restaurantId(2L)
                .status("CREATED")
                .orderedAt(LocalDateTime.now())
                .items(List.of())
                .build();
        given(orderService.create(org.mockito.ArgumentMatchers.any(OrderCreateRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body.id").value(100))
                .andExpect(jsonPath("$.result.resultCode").value(200));
    }
}


