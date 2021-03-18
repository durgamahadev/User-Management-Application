package com.ashokit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.app.entity.User;

public interface UserServiceRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);

}