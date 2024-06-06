package org.example.controller.custom.impl;

import org.example.controller.custom.OrderController;
import org.example.dto.OrderDto;
import org.example.entity.OrderEntity;
import org.example.service.custom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
public class OrderControllerImpl implements OrderController {
    @Autowired
    OrderService service;
    @PostMapping("/order")
    @Override
    public OrderEntity save(@RequestBody OrderDto orderDto) {
        return service.save(orderDto);
    }

    @Override
    public boolean delete(Long value) {
        return false;
    }
    @GetMapping("/orders")
    @Override
    public List<OrderDto> getAll() {
        return service.getAll();
    }
    @GetMapping("/orders/user/{userId}")
    @Override
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId){
        return service.getOrdersByUserId(userId);
    }
    @GetMapping("/order/{id}")
    @Override
    public OrderDto getOrderId(@PathVariable Long id){
        return service.getOrderId(id);
    }

    @DeleteMapping("/order/{id}")
    @Override
    public boolean deleteById(@PathVariable Long id){
        return  service.delete(id);
    }
}
