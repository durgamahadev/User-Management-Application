package com.ashokit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.app.entity.Country;

public interface CountryRepository extends JpaRepository<Country,Integer> {

}
