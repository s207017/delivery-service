package org.delivery.api.account;

import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountApiController.class)
class AccountApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void me_returnsApiOkWithProfile() throws Exception {
        var now = LocalDateTime.of(2025, 1, 1, 12, 0, 0);
        var response = AccountMeResponse.builder()
                .name("Tester")
                .email("tester@example.com")
                .registeredAt(now)
                .build();
        given(accountService.getCurrentAccountProfile()).willReturn(response);

        mockMvc.perform(get("/api/account/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.resultCode").value(200))
                .andExpect(jsonPath("$.result.resultMessage").value("Success"))
                .andExpect(jsonPath("$.body.email").value("tester@example.com"))
                .andExpect(jsonPath("$.body.name").value("Tester"))
                .andExpect(jsonPath("$.body.registeredAt").exists());
    }
}


