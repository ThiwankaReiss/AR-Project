package org.example.service.custom;

import org.example.controller.CrudController;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.service.CrudService;

public interface UserService extends CrudService<UserDto, UserEntity> {
     UserDto authUser( UserDto dto);
}
