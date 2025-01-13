package com.example.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springjwt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	boolean existsByUsername(String username);
}
