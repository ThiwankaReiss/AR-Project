package org.example.service.custom.impl;

import org.example.dto.OrderDetailDto;
import org.example.dto.OrderDto;
import org.example.dto.UserDto;
import org.example.entity.OrderEntity;
import org.example.repository.OrderRepository;
import org.example.service.custom.OrderDetailService;
import org.example.service.custom.OrderService;
import org.example.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository repository;
    @Autowired
    OrderDetailService detailService;
    @Override
    public OrderEntity save(OrderDto orderDto) {
        if(orderDto.getStatus()==null){
            orderDto.setStatus("processing");
        }
        if(orderDto.getDate()==null){
            orderDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if(orderDto.getTime()==null){
            orderDto.setTime(getCurrentTimeFormatted());
        }
        OrderEntity entity=repository.save(
            new OrderEntity(
                    orderDto.getId(),
                    orderDto.getUser().getId(),
                    orderDto.getTotal(),
                    orderDto.getDate(),
                    orderDto.getTime(),
                    orderDto.getStatus()
            ));
        for (OrderDetailDto dto: orderDto.getDetail()){
            dto.setOrderId(entity.getId());
            detailService.save(dto);
        }

        return entity;
    }

    @Override
    public boolean delete(Long value) {
        if(repository.findById(value).isPresent()){
            detailService.deleteByOrderId(value);
            repository.deleteById(value);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDto> getAll() {
        List<OrderEntity> entities= StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        List<OrderDto> dtos=new ArrayList<>();

        for (OrderEntity entity:entities){
            dtos.add(new OrderDto(
                    entity.getId(),
                    userService.getUserById(entity.getUserId()),
                    entity.getTotal(),
                    entity.getDate(),
                    entity.getTime(),
                    entity.getStatus(),
                    detailService.getDetailByOrderId(entity.getId())

            ));

        }

        return dtos;
    }
    private static String getCurrentTimeFormatted() {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h.mma");
        String formattedTime = now.format(formatter).toLowerCase();
        formattedTime = formattedTime.replace("am", "a.m").replace("pm", "p.m");
        return formattedTime;
    }
    @Override
    public List<OrderDto> getOrdersByUserId(Long userId){
        List<OrderEntity> entities= StreamSupport.stream(repository.findByUserId(userId).spliterator(), false).collect(Collectors.toList());
        List<OrderDto> dtos=new ArrayList<>();
        UserDto user=userService.getUserById(userId);
        for (OrderEntity entity:entities){
            dtos.add(new OrderDto(
                    entity.getId(),
                    user,
                    entity.getTotal(),
                    entity.getDate(),
                    entity.getTime(),
                    entity.getStatus(),
                    detailService.getDetailByOrderId(entity.getId())
            ));

        }

        return dtos;
    }

    @Override
    public OrderDto getOrderId(Long id){
        OrderEntity entity=repository.findById(id).orElse(null);
        if(entity==null){
            return null;
        }
        return new OrderDto(
                entity.getId(),
                userService.getUserById(entity.getUserId()),
                entity.getTotal(),
                entity.getDate(),
                entity.getTime(),
                entity.getStatus(),
                detailService.getDetailByOrderId(entity.getId())
        );
    }
}
