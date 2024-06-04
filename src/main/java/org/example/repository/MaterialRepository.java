package org.example.repository;

import org.example.entity.ImageEntity;
import org.example.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    List<MaterialEntity> findByModelId(Long productId);
}
