package com.example.testsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testsecurity.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	boolean existsByUsername(String username);
}
