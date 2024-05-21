package org.example.service;

import java.sql.SQLException;
import java.util.List;

public interface CrudService  <dto,entity>{
    entity save (dto dto) ;
    boolean delete(Long value) ;
    List<dto> getAll() ;
}
