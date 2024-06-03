package org.example.repository;

import org.example.entity.ImageEntity;
import org.example.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
}
