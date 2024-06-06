package org.example.service.custom;

import org.example.controller.CrudController;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.service.CrudService;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService extends CrudService<UserDto, UserEntity> {
     UserDto authUser( UserDto dto);

     UserDto getUserById(Long userId);

     ResponseDto forgotPassword(@RequestBody ResponseDto dto);
}
