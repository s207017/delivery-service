package org.delivery.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    REGISTERED("Registered"),
    UNREGISTERED("Unregistered");

    private final String description;
}
