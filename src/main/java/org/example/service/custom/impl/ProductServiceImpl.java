package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.MaterialDto;
import org.example.dto.ProductDto;
import org.example.dto.UserDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.example.service.custom.MaterialService;
import org.example.service.custom.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;

    @Autowired
    MaterialService materialService;
    @Autowired
    ObjectMapper mapper;
    @Override
    public ProductEntity save(ProductDto productDto) {
        String images="";
        for (Integer img: productDto.getImages()) {
            images+=img+" ";
        }
        ProductEntity entity=repository.save(
                new ProductEntity(
                        productDto.getId(),
                        productDto.getPrice(),
                        productDto.getName(),
                        productDto.getType(),
                        images)
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
        List<ProductEntity> entities=StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        List<ProductDto> dtos=new ArrayList<>();
        for (ProductEntity entity:entities){
            dtos.add(new ProductDto(
                    entity.getId(),
                    entity.getPrice(),
                    entity.getName(),
                    entity.getType(),
                    covertToArray(entity.getImages()),
                    materialService.getByModelId(entity.getId())
            ));
        }

        return dtos;
    }

    private List<Integer> covertToArray(String string){
        String [] stringArray=string.split(" ");
        List<Integer> intArray=new ArrayList<>();
        for(String num: stringArray){
            intArray.add(Integer.parseInt(num));
        }
        return intArray;
    }
}
