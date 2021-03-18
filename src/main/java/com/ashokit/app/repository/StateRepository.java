package com.ashokit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.app.entity.State;

public interface StateRepository extends JpaRepository<State, Integer> {

	public List<State> findByCountryId(Integer countryId);
}
