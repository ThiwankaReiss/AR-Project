package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.MaterialDto;
import org.example.entity.MaterialEntity;
import org.example.repository.MaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MaterialServiceImplTest {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private MaterialRepository repository;

    @Mock
    private ImageServiceImpl imageService;

    @InjectMocks
    private MaterialServiceImpl materialService;

    private MaterialDto materialDto;
    private MaterialEntity materialEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        materialDto = new MaterialDto(1L, 1L, "Material1", "Red", 1L, 1.0, true);
        materialEntity = new MaterialEntity(1L, 1L, "Material1", "Red", 1L, 1.0, true);
    }

    @Test
    void testSave() {
        when(mapper.convertValue(materialDto, MaterialEntity.class)).thenReturn(materialEntity);
        when(repository.save(any(MaterialEntity.class))).thenReturn(materialEntity);

        MaterialEntity result = materialService.save(materialDto);

        assertNotNull(result);
        assertEquals(materialEntity.getId(), result.getId());
        assertEquals(materialEntity.getName(), result.getName());

        verify(mapper, times(1)).convertValue(materialDto, MaterialEntity.class);
        verify(repository, times(1)).save(materialEntity);
    }

    @Test
    void testDelete() {
        boolean result = materialService.delete(1L);

        assertFalse(result);
    }

    @Test
    void testGetAll() {
        List<MaterialDto> result = materialService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetByModelId() {
        List<MaterialEntity> entities = new ArrayList<>();
        entities.add(materialEntity);

        when(repository.findByModelId(1L)).thenReturn(entities);
        when(mapper.convertValue(materialEntity, MaterialDto.class)).thenReturn(materialDto);

        List<MaterialDto> result = materialService.getByModelId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(materialDto.getId(), result.get(0).getId());

        verify(repository, times(1)).findByModelId(1L);
        verify(mapper, times(1)).convertValue(materialEntity, MaterialDto.class);
    }

    @Test
    void testDeleteByProductId() {
        List<MaterialEntity> entities = new ArrayList<>();
        entities.add(materialEntity);

        when(repository.findByModelId(1L)).thenReturn(entities);

        materialService.deleteByProductId(1L);

        verify(imageService, times(1)).deleteImage(materialEntity.getTexture());
        verify(repository, times(1)).deleteByModelId(1L);
    }
}
