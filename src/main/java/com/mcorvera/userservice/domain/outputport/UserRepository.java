package com.mcorvera.userservice.domain.outputport;

import com.mcorvera.userservice.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Iterable<User> getAll();
    Optional<User> get(String id);
    User save(User user);
    Boolean delete(String id);
}
