package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private double price;
    private String name;
    private String type;
    private List<Integer> images;
    private List<MaterialDto> materials;
}
