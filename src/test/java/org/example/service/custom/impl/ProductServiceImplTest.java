package org.example.service.custom.impl;


import org.example.dto.MaterialDto;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.example.service.custom.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ImageServiceImpl imageService;

    @Mock
    private MaterialService materialService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_NewProduct() {
        ProductDto productDto = new ProductDto(null, 100.0, "Test Product", "Type1", List.of(1, 2, 3), new ArrayList<>());
        ProductEntity productEntity = new ProductEntity(1L, 100.0, "Test Product", "Type1", "1 2 3");

        when(repository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductEntity result = productService.save(productDto);

        assertEquals(productEntity.getId(), result.getId());
        verify(repository, times(1)).save(any(ProductEntity.class));
        verify(materialService, times(0)).save(any(MaterialDto.class));
    }

    @Test
    void testSave_ExistingProduct() {
        ProductDto productDto = new ProductDto(1L, 100.0, "Test Product", "Type1", List.of(1, 2, 3), new ArrayList<>());
        ProductEntity existingEntity = new ProductEntity(1L, 100.0, "Test Product", "Type1", "1 2 4");

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(ProductEntity.class))).thenReturn(existingEntity);

        ProductEntity result = productService.save(productDto);

        assertEquals(existingEntity.getId(), result.getId());
        verify(imageService, times(1)).deleteImage(4L);
        verify(repository, times(1)).save(any(ProductEntity.class));
        verify(materialService, times(productDto.getMaterials().size())).save(any(MaterialDto.class));
    }

    @Test
    void testDelete_ProductExists() {
        ProductEntity productEntity = new ProductEntity(1L, 100.0, "Test Product", "Type1", "1 2 3");

        when(repository.findById(1L)).thenReturn(Optional.of(productEntity));

        boolean result = productService.delete(1L);

        assertTrue(result);
        verify(imageService, times(3)).deleteImage(anyLong());
        verify(materialService, times(1)).deleteByProductId(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_ProductDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        boolean result = productService.delete(1L);

        assertFalse(result);
        verify(repository, times(0)).deleteById(1L);
    }

    @Test
    void testGetAll() {
        List<ProductEntity> entities = new ArrayList<>();
        entities.add(new ProductEntity(1L, 100.0, "Test Product", "Type1", "1 2 3"));

        when(repository.findAll()).thenReturn(entities);

        List<ProductDto> result = productService.getAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
        verify(materialService, times(1)).getByModelId(1L);
    }

    @Test
    void testGetProductById_ProductExists() {
        ProductEntity productEntity = new ProductEntity(1L, 100.0, "Test Product", "Type1", "1 2 3");

        when(repository.findById(1L)).thenReturn(Optional.of(productEntity));

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(productEntity.getId(), result.getId());
        verify(repository, times(1)).findById(1L);
        verify(materialService, times(1)).getByModelId(1L);
    }

    @Test
    void testGetProductById_ProductDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ProductDto result = productService.getProductById(1L);

        assertNull(result);
        verify(repository, times(1)).findById(1L);
    }
}
