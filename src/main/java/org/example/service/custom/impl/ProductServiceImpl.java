package org.example.service.custom.impl;

import org.example.dto.MaterialDto;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.example.service.custom.MaterialService;
import org.example.service.custom.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;

    @Autowired
    MaterialService materialService;
    @Override
    public ProductEntity save(ProductDto productDto) {
        ProductEntity entity=repository.save(
                new ProductEntity(
                        productDto.getId(),
                        productDto.getPrice(),
                        productDto.getName(),
                        productDto.getType(),
                        productDto.getImages())
        );
        for(MaterialDto dto : productDto.getMaterials()){
            dto.setModelId(entity.getId());
            materialService.save(dto);
        }
        return entity;
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
