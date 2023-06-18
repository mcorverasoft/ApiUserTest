package com.mcorvera.userservice.domain.outputport;

import com.mcorvera.userservice.domain.model.Phone;

import java.util.Optional;

public interface PhoneRepository {
    Iterable<Phone> getAll();
    Optional<Phone> get(Integer id);
    Phone save(Phone phone);
    Boolean delete(Integer id);
}
