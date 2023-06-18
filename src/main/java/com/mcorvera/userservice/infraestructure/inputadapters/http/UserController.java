package com.mcorvera.userservice.infraestructure.inputadapters.http;

import com.google.common.collect.Iterables;
import com.mcorvera.userservice.application.dtos.UserDTO;
import com.mcorvera.userservice.application.dtos.UserResponse;
import com.mcorvera.userservice.application.inputadapters.UserService;
import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.dtos.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<BaseResponse> registerUser(@RequestBody @Valid User user){
        UserResponse userResponse =userService.createUser(user);
        return ResponseEntity.ok(BaseResponse.builder().data(userResponse).build());
    }
    @GetMapping
    public ResponseEntity<BaseResponse> getAllUser(){
        Iterable<UserDTO> listUser= userService.getAll();
        if(Iterables.size(listUser)==0)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(BaseResponse.builder().data(listUser).build());

    }
}
