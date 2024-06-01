package org.example.controller.custom;

import org.example.controller.CrudController;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController extends CrudController<UserDto, UserEntity> {
     UserDto authUser( UserDto dto);
     ResponseDto localHost();
}
