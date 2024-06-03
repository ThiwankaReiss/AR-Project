package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.MaterialDto;
import org.example.dto.UserDto;
import org.example.entity.MaterialEntity;
import org.example.repository.MaterialRepository;
import org.example.repository.UserRepository;
import org.example.service.custom.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MaterialRepository repository;
    @Override
    public MaterialEntity save(MaterialDto materialDto) {
        return repository.save( mapper.convertValue(materialDto, MaterialEntity.class));
    }

    @Override
    public boolean delete(Long value) {
        return false;
    }

    @Override
    public List<MaterialDto> getAll() {
        return List.of();
    }
}
