package com.mcorvera.userservice.infraestructure.dtos;

import com.mcorvera.userservice.domain.model.Phone;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String name;
    private String email;
    private String token;
    private Instant created;
    private Instant modified;
    private Instant last_login;
    private Boolean isActive;
    private ArrayList<Phone> phones;
}
