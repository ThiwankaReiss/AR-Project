package org.example.controller.custom.impl;

import org.example.controller.custom.ProductController;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.service.custom.ProductService;
import org.example.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin
public class ProductControllerImpl implements ProductController {
    @Autowired
    ProductService service;
    @PostMapping("/model")
    @Override
    public ProductEntity save(@RequestBody ProductDto productDto) {
        return service.save(productDto);
    }

    @Override
    public boolean delete(Long value) {
        return false;
    }

    @Override
    public List<ProductDto> getAll() {
        return List.of();
    }
}
