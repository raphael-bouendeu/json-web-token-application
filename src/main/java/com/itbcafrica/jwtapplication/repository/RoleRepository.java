package com.itbcafrica.jwtapplication.repository;

import com.itbcafrica.jwtapplication.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
