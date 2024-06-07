package org.example.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_UserWithNullStatus_ShouldSetDefaultStatusAndSave() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        // userDto.setStatus(null); // status is null

        UserEntity userEntity = new UserEntity();
        when(mapper.convertValue(userDto, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity savedEntity = userService.save(userDto);

        assertEquals("customer", userDto.getStatus());
        verify(repository, times(1)).save(userEntity);
        assertNotNull(savedEntity);
    }

    @Test
    void save_UserWithStatus_ShouldSaveWithGivenStatus() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setStatus("admin");

        UserEntity userEntity = new UserEntity();
        when(mapper.convertValue(userDto, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity savedEntity = userService.save(userDto);

        assertEquals("admin", userDto.getStatus());
        verify(repository, times(1)).save(userEntity);
        assertNotNull(savedEntity);
    }
    @Test
    void delete_ExistingId_ShouldReturnTrue() {
        Long userId = 1L;
        when(repository.findById(userId)).thenReturn(Optional.of(new UserEntity()));

        boolean result = userService.delete(userId);

        assertTrue(result);
        verify(repository, times(1)).deleteById(userId);
    }

    @Test
    void delete_NonExistingId_ShouldReturnFalse() {
        Long userId = 1L;
        when(repository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.delete(userId);

        assertFalse(result);
        verify(repository, never()).deleteById(userId);
    }
    @Test
    void getAll_ShouldReturnListOfUserDtos() {
        UserEntity userEntity1 = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        when(repository.findAll()).thenReturn(Arrays.asList(userEntity1, userEntity2));
        when(mapper.convertValue(userEntity1, UserDto.class)).thenReturn(new UserDto());
        when(mapper.convertValue(userEntity2, UserDto.class)).thenReturn(new UserDto());

        List<UserDto> userDtos = userService.getAll();

        assertEquals(2, userDtos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void authUser_UserNotFound_ShouldReturnNull() {
        UserDto userDto = new UserDto();
        userDto.setEmail("nonexistent@example.com");
        userDto.setPassword("password");

        when(repository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());

        UserDto result = userService.authUser(userDto);

        assertNull(result);
    }

    @Test
    void authUser_EmptyPassword_ShouldReturnNull() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password");

        when(repository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(userEntity));

        UserDto result = userService.authUser(userDto);

        assertNull(result);
    }
    @Test
    void getUserById_NullId_ShouldReturnNull() {
        Long userId = null;

        UserDto result = userService.getUserById(userId);

        assertNull(result);
    }
    @Test
    void forgotPassword_NullEmail_ShouldReturnFailResponse() {
        ResponseDto requestDto = new ResponseDto();
        requestDto.setResponse(null);

        ResponseDto responseDto = userService.forgotPassword(requestDto);

        assertEquals("Fail", responseDto.getResponse());
    }

    @Test
    void forgotPassword_EmptyEmail_ShouldReturnFailResponse() {
        ResponseDto requestDto = new ResponseDto();
        requestDto.setResponse("");

        ResponseDto responseDto = userService.forgotPassword(requestDto);

        assertEquals("Fail", responseDto.getResponse());
    }

}