package com.mcorvera.userservice.application.inputports;

import com.mcorvera.userservice.domain.model.User;

public interface UserServicePort {
    User createUser(User user);
    Iterable<User> getAll();
}
