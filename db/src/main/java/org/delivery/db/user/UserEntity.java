package org.delivery.db.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.delivery.db.BaseEntity;
import org.delivery.db.user.enums.UserStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user") //to match with DB Table name
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(length = 150, nullable = false)
    private String address;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;
}
