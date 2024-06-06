package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long orderId;
    private Integer amount;
    private double price;
    private ProductDto product;
}
