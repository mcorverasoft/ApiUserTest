package com.mcorvera.userservice.application.outputports;

import com.mcorvera.userservice.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Iterable<User> getAll();

    Optional<User> get(String id);

    User save(User user);

    Boolean delete(String id);
}
