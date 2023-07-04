package com.mcorvera.userservice.infraestructure.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String token;
    private Instant created;
    private Instant modified;
    private Instant last_login;
    private Boolean isActive;
}
