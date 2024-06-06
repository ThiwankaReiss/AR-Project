package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.ImageEntity;
import org.example.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    List<MaterialEntity> findByModelId(Long productId);
    @Modifying
    @Transactional
    void deleteByModelId(Long id);
}
