package com.mcorvera.userservice.infraestructure.dtos.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ErrorBaseResponse {
    @Builder.Default
    private TypeError type=TypeError.GENERIC_ERROR;
    @Builder.Default
    List<String> errors=new ArrayList<>();
    @JsonIgnore
    private String stackTrace;
}
