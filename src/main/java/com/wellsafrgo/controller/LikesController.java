package com.wellsafrgo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsafrgo.model.Likes;
import com.wellsafrgo.model.LikesRequest;
import com.wellsafrgo.repository.LikesRepository;
import com.wellsfargo.response.LikesResponse;
import com.wellsfargo.response.SuccessResponse;


@RestController
@RequestMapping("/api/v1")
public class LikesController {
    
	@Autowired
	private LikesRepository likesRepository;
	
	
	@GetMapping("/likes")
	public List<Likes> getAllUsers() {
		return likesRepository.findAll();
	}

	@GetMapping("/likes/{empid}")
	public ResponseEntity<LikesResponse> getLikesForEmp(@PathVariable(value = "empid") String empid) {
		List<Likes> likes = likesRepository.findbyEmpId(empid);
		List<String> empList = likes.stream().map(i -> {
			String emp = i.getLikedempId();
			return emp;
		}).collect(Collectors.toList());
		LikesResponse response = new LikesResponse();
		response.setEmpIdList(empList);
		response.setTotalLikes(empList.size());
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/likesinsert")
	public ResponseEntity<SuccessResponse> insertLikeForEmp(@Valid @RequestBody LikesRequest likesRequest) {
		Likes likes = new Likes();
	    likes.setEmpId(likesRequest.getEmpId());
	    likes.setLikedempId(likesRequest.getLikedempId());
		likesRepository.save(likes);
		return ResponseEntity.ok().body(new SuccessResponse("Success"));
	}
	

}
