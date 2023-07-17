package com.mcorvera.userservice.infraestructure.dtos.events;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
public abstract class EventBase<T> {
    private UUID id;
    private Instant date;
    private EvenType type;
    private T data;
}
