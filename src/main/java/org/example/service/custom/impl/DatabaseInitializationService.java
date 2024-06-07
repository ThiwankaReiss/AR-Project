package org.example.service.custom.impl;

import jakarta.annotation.PostConstruct;
import org.example.dto.MaterialDto;
import org.example.dto.ProductDto;
import org.example.dto.UserDto;
import org.example.service.custom.ProductService;
import org.example.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseInitializationService {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @PostConstruct
    public void init() {
        insertInitialData();
    }

    public void insertInitialData() {
        if(userService.getAll().size()<3){
            createUsers();
        }
        if(productService.getAll().size()<4){
            createProducts();
        }
    }

    private void createUsers(){
        userService.save(new UserDto(1L,"thiwankar2003@gmail.com","Thiwanka","admin"));
        userService.save(new UserDto(2L,"sachini2001@gmail.com","Sachini","employee"));
        userService.save(new UserDto(3L,"sharada@gmail.com","Sharada","customer"));
    }

    private void createProducts(){
        List<Integer> i=new ArrayList<>();
        List<MaterialDto> wood=new ArrayList<>();
        wood.add(new MaterialDto(null,null,"Cussion",null,0L,20,true));
        productService.save(new ProductDto(null,800,"Wooden Chair","woodenChair",i,wood));

        List<MaterialDto> officeTable=new ArrayList<>();
        officeTable.add(new MaterialDto(null,null,"TopCussion",null,0L,20,true));
        officeTable.add(new MaterialDto(null,null,"BtmCussion",null,0L,20,true));

        productService.save(new ProductDto(null,800,"Office Tables","officeTable",i,officeTable));

        List<MaterialDto> sofa=new ArrayList<>();
        sofa.add(new MaterialDto(null,null,"Pillow",null,0L,20,true));
        sofa.add(new MaterialDto(null,null,"Seat",null,0L,20,true));
        sofa.add(new MaterialDto(null,null,"Frame",null,0L,20,true));
        productService.save(new ProductDto(null,850,"Sofa","sofa",i,sofa));

        List<MaterialDto> picnicTable=new ArrayList<>();
        picnicTable.add(new MaterialDto(null,null,"Table",null,0L,20,true));
        productService.save(new ProductDto(null,850,"Picnic Table","picnicTable",i,picnicTable));


    }

}

