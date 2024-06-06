package org.example.controller.custom;

import org.example.dto.OrderDto;
import org.example.entity.OrderEntity;
import org.example.service.CrudService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderController extends CrudService<OrderDto, OrderEntity> {
    List<OrderDto> getOrdersByUserId(Long userId);

    @GetMapping("/order/{id")
    OrderDto getOrderId(@PathVariable Long id);

    @DeleteMapping("/order/{id}")
    boolean deleteById(@PathVariable Long id);
}
