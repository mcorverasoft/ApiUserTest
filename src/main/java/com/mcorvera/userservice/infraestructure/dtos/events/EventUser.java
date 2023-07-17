package com.mcorvera.userservice.infraestructure.dtos.events;

import com.mcorvera.userservice.domain.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EventUser extends EventBase<User>{
}
