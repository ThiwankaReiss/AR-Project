package org.example.controller.custom;

import org.example.controller.CrudController;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;

public interface UserController extends CrudController<UserDto, UserEntity> {
}
