package org.example.controller.custom.impl;

import org.example.service.custom.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/images")
public class ImageControllerImpl {
    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String id = imageServiceImpl.saveImage(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        try {

            Resource file = imageServiceImpl.getImage(id);
            if(file==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        boolean deleted = imageServiceImpl.deleteImage(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }
}