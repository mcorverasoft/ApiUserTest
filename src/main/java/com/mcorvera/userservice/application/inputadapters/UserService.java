package com.mcorvera.userservice.application.inputadapters;

import com.mcorvera.userservice.application.dtos.UserDTO;
import com.mcorvera.userservice.application.dtos.UserResponse;
import com.mcorvera.userservice.application.inputport.UserServicePort;
import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions.DuplicateResourceException;
import com.mcorvera.userservice.infraestructure.outputadapter.UserRepositoryH2;
import com.mcorvera.userservice.infraestructure.utils.JwtToken;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService implements UserServicePort {
    private final BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();

    private final ModelMapper modelMapper;

    private final JwtToken jwtToken;
    private final UserRepositoryH2 userRepository;

    public UserService(ModelMapper modelMapper, JwtToken jwtToken, UserRepositoryH2 userRepository) {
        this.modelMapper = modelMapper;
        this.jwtToken = jwtToken;
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(User user) {
        //Business Rules
        if(userRepository.existsByEmail(user.getEmail()))
            throw new DuplicateResourceException("email: "+user.getEmail()+" is already registered");

        String username=user.getName().toLowerCase().replaceAll("\\s+","");
        while(userRepository.existsByUsername(username))
            username = user.getName().toLowerCase().replaceAll("\\s+", "") +(new Random()).nextInt(1000);

        user.setUsername(username);
        user.setPassword(bCryptPassEncoder.encode(user.getPassword()));
        user.setToken(jwtToken.generateJwt(user.getUsername()));
        return modelMapper.map(userRepository.save(user),UserResponse.class);
    }

    @Override
    public Iterable<UserDTO> getAll() {
        return modelMapper.map(userRepository.getAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }
}
