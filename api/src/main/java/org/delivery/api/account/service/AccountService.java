package org.delivery.api.account.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    public AccountMeResponse getCurrentAccountProfile() {
        return AccountMeResponse.builder()
                .name("Choi Seunghwan")
                .email("choi@gmail.com")
                .registeredAt(LocalDateTime.now())
                .build();
    }
}


