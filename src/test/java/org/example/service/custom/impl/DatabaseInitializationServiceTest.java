package org.example.service.custom.impl;

import org.example.dto.ProductDto;
import org.example.dto.UserDto;
import org.example.service.custom.ProductService;
import org.example.service.custom.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DatabaseInitializationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private DatabaseInitializationService databaseInitializationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testInsertInitialData_UsersAndProductsExist() {
        List<UserDto> existingUsers = List.of(new UserDto(), new UserDto(), new UserDto());
        List<ProductDto> existingProducts = List.of(new ProductDto(), new ProductDto(), new ProductDto(), new ProductDto());

        when(userService.getAll()).thenReturn(existingUsers);
        when(productService.getAll()).thenReturn(existingProducts);

        databaseInitializationService.insertInitialData();

        verify(userService, never()).save(any(UserDto.class));
        verify(productService, never()).save(any(ProductDto.class));
    }


}
