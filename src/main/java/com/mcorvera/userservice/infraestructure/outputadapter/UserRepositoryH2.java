package com.mcorvera.userservice.infraestructure.outputadapter;

import com.mcorvera.userservice.domain.model.User;
import com.mcorvera.userservice.application.outputports.UserRepository;
import com.mcorvera.userservice.infraestructure.entity.UserEntity;
import com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryH2 implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    private final ModelMapper modelMapper;

    public UserRepositoryH2(UserJpaRepository userJpaRepository, ModelMapper modelMapper) {
        this.userJpaRepository = userJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Iterable<User> getAll() {
        return modelMapper.map(userJpaRepository.findAll(), new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public Optional<User> get(String id) {
        UserEntity user = this.userJpaRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not Found")
        );
        return Optional.of(modelMapper.map(user,User.class));
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        return modelMapper.map(this.userJpaRepository.save(userEntity),User.class);
    }

    @Override
    public Boolean delete(String id) {
        UserEntity userEntity = this.userJpaRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("User not Found")
        );
        this.userJpaRepository.deleteById(userEntity.getId());
        return true;
    }

    public Boolean existsByEmail (String email){
        return  this.userJpaRepository.existsByEmail(email);
    }
    public Boolean existsByUsername (String username){
        return  this.userJpaRepository.existsByUsername (username);
    }


}
