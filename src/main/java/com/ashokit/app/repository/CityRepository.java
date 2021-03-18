package com.ashokit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.app.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	public List<City> findByStateId(Integer stateId);
}
