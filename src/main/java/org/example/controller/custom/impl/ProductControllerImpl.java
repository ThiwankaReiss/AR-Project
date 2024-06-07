package org.example.controller.custom.impl;

import org.example.controller.custom.ProductController;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.service.custom.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/model/{id}")
    @Override
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @GetMapping("/model")
    @Override
    public List<ProductDto> getAll() {
        return service.getAll();
    }
}
