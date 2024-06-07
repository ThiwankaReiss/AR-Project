package org.example.service.custom.impl;


import org.example.entity.ImageEntity;
import org.example.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private ImageRepository repository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private final String storageLocation = "src\\main\\resources\\img";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test image content".getBytes());

        ImageEntity entity = new ImageEntity();
        entity.setId(1L);
        entity.setOriginalName("image.jpg");
        ImageEntity savedEntity = new ImageEntity();
        savedEntity.setId(1L);
        savedEntity.setOriginalName("image.jpg");
        savedEntity.setNewName("1.jpg");

        when(repository.save(any(ImageEntity.class))).thenReturn(savedEntity);

        String result = imageService.saveImage(file);

        assertNotNull(result);
        assertEquals("1", result);

        Path filePath = Paths.get(storageLocation, "1.jpg");
        assertTrue(Files.exists(filePath));

        // Cleanup
        Files.deleteIfExists(filePath);

        verify(repository, times(2)).save(any(ImageEntity.class));
    }

    @Test
    void testGetImage() throws IOException {
        ImageEntity entity = new ImageEntity();
        entity.setId(1L);
        entity.setNewName("1.jpg");

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        Path filePath = Paths.get(storageLocation, "1.jpg");
        Files.createFile(filePath);  // Create a dummy file for testing

        Resource resource = imageService.getImage(1L);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertTrue(resource.isReadable());

        // Cleanup
        Files.deleteIfExists(filePath);

        verify(repository, times(1)).findById(1L);
    }



    @Test
    void testDeleteImage() throws IOException {
        ImageEntity entity = new ImageEntity();
        entity.setId(1L);
        entity.setNewName("1.jpg");

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        Path filePath = Paths.get(storageLocation, "1.jpg");
        Files.createFile(filePath);  // Create a dummy file for testing

        boolean result = imageService.deleteImage(1L);

        assertTrue(result);
        assertFalse(Files.exists(filePath));

        verify(repository, times(1)).deleteById(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testDeleteImage_FileNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        boolean result = imageService.deleteImage(1L);

        assertFalse(result);

        verify(repository, times(1)).findById(1L);
    }
}
