package org.example.service.custom.impl;

import org.example.entity.ImageEntity;
import org.example.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl {

    @Autowired
    ImageRepository repository;
    private String storageLocation="src\\main\\resources\\img";

    public String saveImage(MultipartFile file) throws IOException {
        ImageEntity entity=new ImageEntity();
        entity.setOriginalName(file.getOriginalFilename());
        String fileExtension = getFileExtension(file.getOriginalFilename());
        ImageEntity savedEntity=repository.save(entity);
        String newFileName = savedEntity.getId()+ fileExtension;
        savedEntity.setNewName(newFileName);
        repository.save(savedEntity);
        Path filePath = Paths.get(storageLocation, newFileName);
        Files.copy(file.getInputStream(), filePath);

        return String.valueOf(savedEntity.getId());
    }

    public Resource getImage(Long id) throws IOException  {
        ImageEntity entity =repository.findById(id).orElse(null);
        if(entity==null){
            return null;
        }
        Path filePath = Paths.get(storageLocation, entity.getNewName());
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("Could not read the file: " + id);
        }
    }

    public boolean deleteImage(Long id) {
        ImageEntity entity =repository.findById(id).orElse(null);
        if(entity==null){
            return false;
        }
        Path filePath = Paths.get(storageLocation, entity.getNewName());
        try {
            repository.deleteById(id);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }
    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0) {
            return fileName.substring(lastIndex);
        }
        return "";
    }
   
}