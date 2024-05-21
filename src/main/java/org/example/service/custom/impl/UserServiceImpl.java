package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    UserRepository repository;

    @Override
    public UserEntity save(UserDto userDto) {
        return repository.save(mapper.convertValue(userDto,UserEntity.class));
    }

    @Override
    public boolean delete(Long id) {
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(entity -> mapper.convertValue(entity, UserDto.class))
                .collect(Collectors.toList());
    }

}