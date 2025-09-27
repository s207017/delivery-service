package org.delivery.api.auth.model;

import lombok.Data;
import org.delivery.db.user.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
    
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
    
    @NotBlank
    @Size(max = 150)
    private String address;
    
    private UserRole role = UserRole.CUSTOMER;
}
