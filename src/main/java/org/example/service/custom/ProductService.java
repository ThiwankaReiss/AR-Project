package org.example.service.custom;

import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.service.CrudService;

public interface ProductService  extends CrudService<ProductDto, ProductEntity> {
    ProductDto getProductById(Long id);
}
