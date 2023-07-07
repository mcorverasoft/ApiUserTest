package com.mcorvera.userservice.infraestructure.inputadapters.http;

import com.google.common.collect.Iterables;
import com.mcorvera.userservice.infraestructure.dtos.UserDTO;
import com.mcorvera.userservice.infraestructure.dtos.UserResponse;
import com.mcorvera.userservice.application.services.UserService;
import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.dtos.api.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<BaseResponse> registerUser(@RequestBody @Valid User user){
        UserResponse userResponse = this.modelMapper.map(userService.createUser(user), UserResponse.class);
        return ResponseEntity.ok(BaseResponse.builder().data(userResponse).build());
    }
    @GetMapping
    public ResponseEntity<BaseResponse> getAllUser(){
        Iterable<UserDTO> listUser=
         modelMapper.map(userService.getAll(), new TypeToken<List<UserDTO>>() {}.getType());

        if(Iterables.size(listUser)==0)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(BaseResponse.builder().data(listUser).build());

    }
}
