package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity> findByPassword(String password);
    Optional<UserEntity>  findByEmail(String email);
}
