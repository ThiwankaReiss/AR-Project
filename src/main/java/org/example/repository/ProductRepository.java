package org.example.repository;

import org.example.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {

}
