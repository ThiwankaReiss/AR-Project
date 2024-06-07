package org.example.service.custom.impl;

import org.example.dto.OrderDetailDto;
import org.example.dto.OrderDto;
import org.example.dto.UserDto;
import org.example.entity.OrderEntity;
import org.example.repository.OrderRepository;
import org.example.service.custom.OrderDetailService;
import org.example.service.custom.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderDetailService detailService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_NewOrder() {
        UserDto userDto = new UserDto(1L, "John", "Doe",null);
        OrderDto orderDto = new OrderDto(null, userDto, 100.0, null, null, null, new ArrayList<>());
        OrderEntity orderEntity = new OrderEntity(1L, 1L, 100.0, LocalDate.now().toString(), "12.00p.m", "processing");

        when(repository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        OrderEntity result = orderService.save(orderDto);

        assertNotNull(result);
        assertEquals(orderEntity.getId(), result.getId());
        assertEquals("processing", orderDto.getStatus());
        assertNotNull(orderDto.getDate());
        assertNotNull(orderDto.getTime());

        verify(repository, times(1)).save(any(OrderEntity.class));
        verify(detailService, times(orderDto.getDetail().size())).save(any(OrderDetailDto.class));
    }

    @Test
    void testSave_ExistingOrder() {
        UserDto userDto = new UserDto(1L, "John", "Doe",null);
        OrderDto orderDto = new OrderDto(1L, userDto, 100.0, null, null, null, new ArrayList<>());
        OrderEntity existingEntity = new OrderEntity(1L, 1L, 100.0, LocalDate.now().toString(), "12.00p.m", "processing");

        when(repository.save(any(OrderEntity.class))).thenReturn(existingEntity);

        OrderEntity result = orderService.save(orderDto);

        assertNotNull(result);
        assertEquals(existingEntity.getId(), result.getId());

        verify(repository, times(1)).save(any(OrderEntity.class));
        verify(detailService, times(orderDto.getDetail().size())).save(any(OrderDetailDto.class));
    }

    @Test
    void testDelete_OrderExists() {
        OrderEntity orderEntity = new OrderEntity(1L, 1L, 100.0, "2023-01-01", "12.00p.m", "processing");

        when(repository.findById(1L)).thenReturn(Optional.of(orderEntity));

        boolean result = orderService.delete(1L);

        assertTrue(result);
        verify(detailService, times(1)).deleteByOrderId(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_OrderDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        boolean result = orderService.delete(1L);

        assertFalse(result);
        verify(detailService, times(0)).deleteByOrderId(1L);
        verify(repository, times(0)).deleteById(1L);
    }

    @Test
    void testGetAll() {
        List<OrderEntity> entities = new ArrayList<>();
        entities.add(new OrderEntity(1L, 1L, 100.0, "2023-01-01", "12.00p.m", "processing"));

        when(repository.findAll()).thenReturn(entities);
        when(userService.getUserById(1L)).thenReturn(new UserDto(1L, "John", "Doe",null));
        when(detailService.getDetailByOrderId(1L)).thenReturn(new ArrayList<>());

        List<OrderDto> result = orderService.getAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
        verify(userService, times(1)).getUserById(1L);
        verify(detailService, times(1)).getDetailByOrderId(1L);
    }

    @Test
    void testGetOrdersByUserId() {
        List<OrderEntity> entities = new ArrayList<>();
        entities.add(new OrderEntity(1L, 1L, 100.0, "2023-01-01", "12.00p.m", "processing"));
        UserDto userDto = new UserDto(1L, "John", "Doe",null);

        when(repository.findByUserId(1L)).thenReturn(entities);
        when(userService.getUserById(1L)).thenReturn(userDto);
        when(detailService.getDetailByOrderId(1L)).thenReturn(new ArrayList<>());

        List<OrderDto> result = orderService.getOrdersByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(userDto, result.get(0).getUser());
        verify(repository, times(1)).findByUserId(1L);
        verify(userService, times(1)).getUserById(1L);
        verify(detailService, times(1)).getDetailByOrderId(1L);
    }

    @Test
    void testGetOrderId_OrderExists() {
        OrderEntity orderEntity = new OrderEntity(1L, 1L, 100.0, "2023-01-01", "12.00p.m", "processing");

        when(repository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(userService.getUserById(1L)).thenReturn(new UserDto(1L, "John", "Doe",null));
        when(detailService.getDetailByOrderId(1L)).thenReturn(new ArrayList<>());

        OrderDto result = orderService.getOrderId(1L);

        assertNotNull(result);
        assertEquals(orderEntity.getId(), result.getId());
        verify(repository, times(1)).findById(1L);
        verify(userService, times(1)).getUserById(1L);
        verify(detailService, times(1)).getDetailByOrderId(1L);
    }

    @Test
    void testGetOrderId_OrderDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        OrderDto result = orderService.getOrderId(1L);

        assertNull(result);
        verify(repository, times(1)).findById(1L);
    }
}
