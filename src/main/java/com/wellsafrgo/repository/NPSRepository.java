package com.wellsafrgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wellsafrgo.model.NamePronounciationRecord;

@Repository
public interface NPSRepository extends JpaRepository<NamePronounciationRecord,String> {

	
	@Query("select c from NamePronounciationRecord c where c.country_code = :countryCode and (c.first_name like %:name% or c.last_name like %:name% or c.preferred_name like %:name%)")
    List<NamePronounciationRecord> getFilesbyName(@Param("name") String name, @Param("countryCode") String countryCode);
	

	@Query("select c from NamePronounciationRecord c where c.country_code = :countryCode ")
    List<NamePronounciationRecord> getFilesbycountry( @Param("countryCode") String countryCode);
	
}
