package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.EmailDto;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    ObjectMapper mapper;

    @Override
    public UserEntity save(UserDto userDto) {
        if(userDto.getStatus()==null){
            userDto.setStatus("customer");
        }
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

    @Override
    public UserDto authUser(UserDto dto) {
        Optional<UserEntity> userByEmail = repository.findByEmail(dto.getEmail());
        if (userByEmail.isPresent()) {
            UserEntity user = userByEmail.get();
            if (user.getPassword().equals(dto.getPassword())) {
                return mapper.convertValue(user, UserDto.class);
            }
        }
        return null;
    }
    @Override
    public UserDto getUserById(Long userId){
        return mapper.convertValue(repository.findById(userId),UserDto.class);
    }
    @Override
    public ResponseDto forgotPassword(@RequestBody ResponseDto dto){

       if(repository.findByEmail(dto.getResponse()).isPresent()){
           UserEntity entity=repository.findByEmail(dto.getResponse()).orElse(null);
           String content ="Dear customer your password is "+entity.getPassword();
           EmailDto emailDto=new EmailDto(dto.getResponse(), "Password Recovery Wood Shop",content,null);
           emailDto.sendEmail();
           return new ResponseDto("Success");
       }
        return new ResponseDto("Fail");
    }
}
