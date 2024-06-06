package org.example.repository;

import org.example.entity.MaterialEntity;
import org.example.entity.OrderEntity;
import org.example.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity,Long> {
    List<OrderEntity> findByUserId(Long userId);
}
