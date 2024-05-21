package org.example.controller;

import java.sql.SQLException;
import java.util.List;

public interface CrudController<dto,entity>{
    entity save (dto dto) ;
    boolean delete(Long value) ;
    List<dto> getAll();
}
