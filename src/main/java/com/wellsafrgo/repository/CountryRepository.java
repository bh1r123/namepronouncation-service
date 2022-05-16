package com.wellsafrgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsafrgo.model.CountryRecord;

@Repository
public interface CountryRepository extends JpaRepository<CountryRecord,String> {

}
