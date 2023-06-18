package com.mcorvera.userservice.infraestructure.outputadapter;

import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Boolean existsByEmail (String email);
    Boolean existsByUsername (String username);
}
