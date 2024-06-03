package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDto {
    private Long id;
    private Long modelId;
    private String name;
    private String color;
    private String texture;
    private double repeate;
    private String visible;
}
