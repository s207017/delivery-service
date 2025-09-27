package org.delivery.api.account.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest {

    @Test
    void getCurrentAccountProfile_returnsFields() {
        var service = new AccountService();
        var dto = service.getCurrentAccountProfile();
        assertThat(dto.getEmail()).isNotBlank();
        assertThat(dto.getName()).isNotBlank();
        assertThat(dto.getRegisteredAt()).isNotNull();
    }
}


