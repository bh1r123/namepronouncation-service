package com.wellsafrgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wellsafrgo.model.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>{

	@Query("SELECT l FROM Likes l WHERE l.empId = :empId")
    public List<Likes> findbyEmpId(@Param("empId") String empId);

}