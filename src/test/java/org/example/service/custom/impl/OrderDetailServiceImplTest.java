package org.example.service.custom.impl;

import org.example.dto.OrderDetailDto;
import org.example.dto.ProductDto;
import org.example.entity.OrderDetailEntity;
import org.example.repository.OrderDetailRepository;
import org.example.service.custom.ProductService;
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

public class OrderDetailServiceImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private OrderDetailRepository repository;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ProductDto productDto = new ProductDto(1L, 100.0, "Product1", "Type1", new ArrayList<>(), new ArrayList<>());
        OrderDetailDto orderDetailDto = new OrderDetailDto(1L, 1L, 2, 200.0, productDto);
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity(1L, 1L, 2, 200.0, 1L);

        when(repository.save(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);

        OrderDetailEntity result = orderDetailService.save(orderDetailDto);

        assertNotNull(result);
        assertEquals(orderDetailEntity.getId(), result.getId());
        assertEquals(orderDetailEntity.getOrderId(), result.getOrderId());
        assertEquals(orderDetailEntity.getAmount(), result.getAmount());
        assertEquals(orderDetailEntity.getPrice(), result.getPrice());
        assertEquals(orderDetailEntity.getModalId(), result.getModalId());

        verify(repository, times(1)).save(any(OrderDetailEntity.class));
    }

    @Test
    void testDelete_OrderDetailExists() {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity(1L, 1L, 2, 200.0, 1L);

        when(repository.findById(1L)).thenReturn(Optional.of(orderDetailEntity));

        boolean result = orderDetailService.delete(1L);

        assertTrue(result);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_OrderDetailDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        boolean result = orderDetailService.delete(1L);

        assertFalse(result);
        verify(repository, times(0)).deleteById(1L);
    }

    @Test
    void testGetDetailByOrderId() {
        List<OrderDetailEntity> entities = new ArrayList<>();
        entities.add(new OrderDetailEntity(1L, 1L, 2, 200.0, 1L));
        ProductDto productDto = new ProductDto(1L, 100.0, "Product1", "Type1", new ArrayList<>(), new ArrayList<>());

        when(repository.findByOrderId(1L)).thenReturn(entities);
        when(productService.getProductById(1L)).thenReturn(productDto);

        List<OrderDetailDto> result = orderDetailService.getDetailByOrderId(1L);

        assertEquals(1, result.size());
        assertEquals(entities.get(0).getId(), result.get(0).getId());
        assertEquals(productDto, result.get(0).getProduct());

        verify(repository, times(1)).findByOrderId(1L);
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testDeleteByOrderId() {
        orderDetailService.deleteByOrderId(1L);

        verify(repository, times(1)).deleteByOrderId(1L);
    }
}
