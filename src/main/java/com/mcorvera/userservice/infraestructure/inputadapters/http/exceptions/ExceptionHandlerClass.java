package com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcorvera.userservice.infraestructure.dtos.api.BaseResponse;
import com.mcorvera.userservice.infraestructure.dtos.api.ErrorBaseResponse;
import com.mcorvera.userservice.infraestructure.dtos.api.TypeError;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerClass {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private ObjectMapper objectMapper;
    private ObjectMapper mapper = new ObjectMapper();


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResponse handleValidationException(MethodArgumentNotValidException ex) {

        ErrorBaseResponse errorBaseResponse = ErrorBaseResponse.builder()
                .type(TypeError.VALIDATION)
                .errors((ex.getBindingResult()
                        .getAllErrors()
                        .stream().map(objectError -> {
                            String errorMessage = ((FieldError) objectError).getField() + ", " + (objectError).getDefaultMessage();
                            return "The field " + errorMessage;
                        }).collect(Collectors.toList())))
                .stackTrace(ex.getStackTrace().toString())
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .successful(false)
                .status(HttpStatus.BAD_REQUEST)
                .message("ERROR")
                .data(0)
                .ErrorResponse(errorBaseResponse)
                .build();
        log.error(ex.getMessage());
        return baseResponse;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public BaseResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorBaseResponse errorBaseResponse = ErrorBaseResponse.builder()
                .type(TypeError.VALIDATION)
                .errors(Collections.singletonList(ex.getMessage()))
                .stackTrace(Arrays.toString(ex.getStackTrace()))
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .successful(false)
                .status(HttpStatus.BAD_REQUEST)
                .message("ERROR")
                .data(0)
                .ErrorResponse(errorBaseResponse)
                .build();
        log.error(ex.getMessage());
        return baseResponse;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleGenericException(Exception ex) {
        String stack = Arrays.toString(ex.getStackTrace());
        ErrorBaseResponse errorBaseResponse = ErrorBaseResponse.builder()
                .type(TypeError.RUNTIME_ERROR)
                .errors(Collections.singletonList(ex.getMessage()))
                .stackTrace(stack)
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .successful(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("ERROR")
                .data(0)
                .ErrorResponse(errorBaseResponse)
                .build();
        log.error(ex.getMessage());
        return baseResponse;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateResourceException.class)
    public BaseResponse handleConstraintException(Exception ex) {
        String stack = Arrays.toString(ex.getStackTrace());
        ErrorBaseResponse errorBaseResponse = ErrorBaseResponse.builder()
                .type(TypeError.RUNTIME_ERROR)
                .errors(Collections.singletonList(ex.getMessage()))
                .stackTrace(stack)
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .successful(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("ERROR")
                .data(0)
                .ErrorResponse(errorBaseResponse)
                .build();
        log.error(ex.getMessage());
        return baseResponse;
    }
}
