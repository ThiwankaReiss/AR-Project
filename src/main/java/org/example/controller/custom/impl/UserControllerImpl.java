package org.example.controller.custom.impl;

import org.example.controller.custom.UserController;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
public class UserControllerImpl implements UserController {
    @Autowired
    UserService service;
    @PostMapping("/user")
    @Override
    public UserEntity save(@RequestBody UserDto dto) {
        return service.save(dto);
    }
    @DeleteMapping("/user/{id}")
    @Override
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @GetMapping("/user")
    @Override
    public List<UserDto> getAll() {
        return service.getAll();
    }
    @PostMapping("/auth")
    @Override
    public UserDto authUser(@RequestBody UserDto dto){
        return service.authUser(dto);
    }
    @GetMapping("/local")
    @Override
    public ResponseDto localHost(){
        return new ResponseDto("Running at Local host");
    }
    @PostMapping("/forgot")
    @Override
    public ResponseDto forgotPassword(@RequestBody ResponseDto dto){
        return service.forgotPassword(dto);
    }

}
