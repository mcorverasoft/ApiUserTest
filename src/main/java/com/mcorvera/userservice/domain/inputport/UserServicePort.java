package com.mcorvera.userservice.domain.inputport;

import com.mcorvera.userservice.application.dtos.UserDTO;
import com.mcorvera.userservice.application.dtos.UserResponse;
import com.mcorvera.userservice.domain.model.User;

public interface UserServicePort {
    User createUser(User user);
    Iterable<User> getAll();
}
