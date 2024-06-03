package org.example.controller.custom;

import org.example.controller.CrudController;
import org.example.dto.ProductDto;
import org.example.dto.UserDto;
import org.example.entity.ProductEntity;
import org.example.entity.UserEntity;

public interface ProductController extends CrudController<ProductDto, ProductEntity> {
}
