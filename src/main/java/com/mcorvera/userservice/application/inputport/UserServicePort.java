package com.mcorvera.userservice.application.inputport;

import com.mcorvera.userservice.application.dtos.UserDTO;
import com.mcorvera.userservice.application.dtos.UserResponse;
import com.mcorvera.userservice.domain.model.User;

public interface UserServicePort {
    UserResponse createUser(User user);
    Iterable<UserDTO> getAll();
}
