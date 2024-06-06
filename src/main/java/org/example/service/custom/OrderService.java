package org.example.service.custom;

import org.example.dto.OrderDto;
import org.example.dto.ProductDto;
import org.example.entity.OrderEntity;
import org.example.entity.ProductEntity;
import org.example.service.CrudService;

import java.util.List;

public interface OrderService extends CrudService<OrderDto, OrderEntity> {
    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto getOrderId(Long id);
}
