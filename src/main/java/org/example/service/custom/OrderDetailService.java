package org.example.service.custom;

import org.example.dto.OrderDetailDto;
import org.example.entity.OrderDetailEntity;
import org.example.service.CrudService;

import java.util.List;

public interface OrderDetailService extends CrudService<OrderDetailDto, OrderDetailEntity> {
    List<OrderDetailDto> getDetailByOrderId(Long orderId);
    void deleteByOrderId(Long id);
}
