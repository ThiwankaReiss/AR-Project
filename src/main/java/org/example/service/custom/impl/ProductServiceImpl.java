package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.MaterialDto;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.example.service.custom.MaterialService;
import org.example.service.custom.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;
    @Autowired
    ImageServiceImpl imageService;
    @Autowired
    MaterialService materialService;
    @Autowired
    ObjectMapper mapper;
    @Override
    public ProductEntity save(ProductDto productDto) {
        if(productDto.getId()!=null){
            ProductEntity entity=repository.findById(productDto.getId()).orElse(null);
            List<Integer> deleteImages=getDifference(covertToArray(entity.getImages()),productDto.getImages());
            for(Integer i:deleteImages){
                imageService.deleteImage(Long.parseLong(i+""));
            }
        }
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
            dto.setVisible(true);
            materialService.save(dto);
        }
        return entity;
    }

    @Override
    public boolean delete(Long value) {

        if(repository.findById(value).isPresent()){
            ProductEntity entity=repository.findById(value).orElse(null);
            List<Integer> images=covertToArray(entity.getImages());
            for(Integer i:images){
                imageService.deleteImage(Long.parseLong(i+""));
            }
            materialService.deleteByProductId(value);
            repository.deleteById(value);
            return true;
        }
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

    @Override
    public ProductDto getProductById(Long id){
        ProductEntity entity=repository.findById(id).orElse(null);

        return new ProductDto(
                entity.getId(),
                entity.getPrice(),
                entity.getName(),
                entity.getType(),
                covertToArray(entity.getImages()),
                materialService.getByModelId(entity.getId())
        );
    }

    private List<Integer> covertToArray(String string){
        List<Integer> intArray=new ArrayList<>();
        if(string==null|| string.equals("")){
            return intArray;
        }
        String [] stringArray=string.split(" ");

        for(String num: stringArray){
            intArray.add(Integer.parseInt(num));
        }
        return intArray;
    }

    private List<Integer> getDifference(List<Integer> oldArray, List<Integer> newArray) {
        ArrayList<Integer> resultList = new ArrayList<>();

        for (int num : oldArray) {
            boolean found = false;
            for (int newNum : newArray) {
                if (num == newNum) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                resultList.add(num);
            }
        }

        return resultList;
    }
}
