package org.example.service.custom.impl;

import org.example.dto.OrderDetailDto;
import org.example.entity.OrderDetailEntity;
import org.example.repository.OrderDetailRepository;
import org.example.service.custom.OrderDetailService;
import org.example.service.custom.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderDetailRepository repository;
    @Override
    public OrderDetailEntity save(OrderDetailDto dto) {

        return repository.save(
                new OrderDetailEntity(
                        dto.getId(),
                        dto.getOrderId(),
                        dto.getAmount(),
                        dto.getPrice(),
                        dto.getProduct().getId()
                ));
    }

    @Override
    public boolean delete(Long value) {
        if(repository.findById(value).isPresent()){
            repository.deleteById(value);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDetailDto> getAll() {
        return List.of();
    }

    @Override
    public List<OrderDetailDto> getDetailByOrderId(Long orderId){
        List<OrderDetailEntity> entities= StreamSupport.stream(repository.findByOrderId(orderId).spliterator(), false).collect(Collectors.toList());
        List<OrderDetailDto> dtos= new ArrayList<>();
        for (OrderDetailEntity entity:entities){
            dtos.add(new OrderDetailDto(
                    entity.getId(),
                    entity.getOrderId(),
                    entity.getAmount(),
                    entity.getPrice(),
                    productService.getProductById(entity.getModalId())
            ));
        }
        return  dtos;
    }
    @Override
    public void deleteByOrderId(Long id){
        repository.deleteByOrderId(id);
    }
}
