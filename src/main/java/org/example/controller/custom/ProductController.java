package org.example.controller.custom;

import org.example.controller.CrudController;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;

public interface ProductController extends CrudController<ProductDto, ProductEntity> {
}
