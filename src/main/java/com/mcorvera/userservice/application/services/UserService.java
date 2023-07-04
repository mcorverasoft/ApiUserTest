package com.mcorvera.userservice.application.services;

import com.mcorvera.userservice.application.inputports.UserServicePort;
import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions.DuplicateResourceException;
import com.mcorvera.userservice.infraestructure.outputadapter.UserRepositoryH2;
import com.mcorvera.userservice.infraestructure.utils.JwtToken;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class UserService implements UserServicePort {
    private final BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();

    private final ModelMapper modelMapper;

    private final JwtToken jwtToken;
    private final UserRepositoryH2 userRepository;

    @Override
    public User createUser(User user) {
        //Business Rules
        if(userRepository.existsByEmail(user.getEmail()))
            throw new DuplicateResourceException("email: "+user.getEmail()+" is already registered");

        String username=user.getName().toLowerCase().replaceAll("\\s+","");
        while(userRepository.existsByUsername(username))
            username = user.getName().toLowerCase().replaceAll("\\s+", "") +(new Random()).nextInt(1000);

        user.setUsername(username);
        user.setPassword(bCryptPassEncoder.encode(user.getPassword()));
        user.setToken(jwtToken.generateJwt(user.getUsername()));
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> getAll() {
      return userRepository.getAll();
    }
}
