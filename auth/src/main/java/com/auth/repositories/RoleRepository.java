package com.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findByName(String name);
}
