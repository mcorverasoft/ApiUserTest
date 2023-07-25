package com.mcorvera.userservice.application.services;

import com.mcorvera.userservice.application.inputports.UserServicePort;
import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.infraestructure.dtos.events.EvenType;
import com.mcorvera.userservice.infraestructure.dtos.events.EventBase;
import com.mcorvera.userservice.infraestructure.dtos.events.EventUser;
import com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions.DuplicateResourceException;
import com.mcorvera.userservice.infraestructure.outputadapter.UserRepositoryH2;
import com.mcorvera.userservice.infraestructure.utils.JwtToken;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {
    private final BCryptPasswordEncoder bCryptPassEncoder;
    private final ModelMapper modelMapper;
    private final JwtToken jwtToken;
    private final UserRepositoryH2 userRepository;
    private final KafkaTemplate<String, EventBase<?>> producer;
    @Value("${spring.kafka.template-default-topic}")
    private String topic;

    @Override
    public User createUser(User user) {
        //Business Rules
        if(userRepository.existsByEmail(user.getEmail()))
            throw new DuplicateResourceException("email: "+user.getEmail()+" is already registered");
        user.setUsername(this.generateUniqueUsername(user.getName()));
        user.setPassword(bCryptPassEncoder.encode(user.getPassword()));
        user.setToken(jwtToken.generateJwt(user.getUsername()));
        User createdUser=userRepository.save(user);
        if(createdUser!=null){
            EventBase<User> eventUser= (EventBase)EventUser.builder()
                    .id(UUID.randomUUID())
                    .date(Instant.now())
                    .type(EvenType.CREATED)
                    .data(createdUser)
                    .build();
            producer.send(topic, eventUser);
        }

        return createdUser;
    }

    @Override
    public Iterable<User> getAll() {
      return userRepository.getAll();
    }

    private String generateUniqueUsername(String name) {
        String username = name.toLowerCase().replaceAll("\\s+", "");
        Random random = new Random();
        while (userRepository.existsByUsername(username)) {
            username = name.toLowerCase().replaceAll("\\s+", "") + random.nextInt(1000);
        }
        return username;
    }
}
