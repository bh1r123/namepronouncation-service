package com.wellsafrgo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "likes")
public class Likes {

	private long id;
	private String empId;
	private String likedempId;

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long  getId() {
		return id;
	}
	public void setId(long  id) {
		this.id = id;
	}
	
	@Column(name = "EMPID", nullable = false)
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	@Column(name = "LIKE_EMPID", nullable = false)
	public String getLikedempId() {
		return likedempId;
	}
	public void setLikedempId(String likedempId) {
		this.likedempId = likedempId;
	}
	


}
