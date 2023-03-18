package com.itbcafrica.jwtapplication.repository;

import com.itbcafrica.jwtapplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
