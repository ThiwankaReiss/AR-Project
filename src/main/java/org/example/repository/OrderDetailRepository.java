package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetailEntity,Long> {
    List<OrderDetailEntity> findByOrderId(Long orderId);
    @Modifying
    @Transactional
    void deleteByOrderId(Long id);
}
