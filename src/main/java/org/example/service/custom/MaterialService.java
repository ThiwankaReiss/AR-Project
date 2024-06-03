package org.example.service.custom;

import org.example.dto.MaterialDto;
import org.example.dto.ProductDto;
import org.example.entity.MaterialEntity;
import org.example.entity.ProductEntity;
import org.example.service.CrudService;

public interface MaterialService extends CrudService<MaterialDto, MaterialEntity> {
}
