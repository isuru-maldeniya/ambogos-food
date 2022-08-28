package com.example.ambigosdemo.platform.repositories;

import com.example.ambigosdemo.platform.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> getUserRoleByName(String name);
}
