package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private UserDto user;
    private double total;
    private String date;
    private String time;
    private String status;
    private List<OrderDetailDto> detail;
}
