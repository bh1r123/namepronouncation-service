package com.wellsafrgo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wells.constants.ActionEnum;
import com.wellsafrgo.exception.ResourceNotFoundException;
import com.wellsafrgo.model.Notifications;
import com.wellsafrgo.model.NotificationsRequest;
import com.wellsafrgo.repository.NotificationsService;
import com.wellsfargo.response.SuccessResponse;


@RestController
@RequestMapping("/api/v1")
public class NotificationsController {
    
	@Autowired
	private NotificationsService notificationsService;
	
	
	@GetMapping("/getAllNotifications")
	public ResponseEntity<List<NotificationsRequest>> getAllNotifications() {
		List<NotificationsRequest> list  = notificationsService.getAllNotifications();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/getAllUnreadNotifications")
	public ResponseEntity<List<NotificationsRequest>> getAllUnreadNotifications() {
		List<NotificationsRequest> list  = notificationsService.getAllUnreadNotifications();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/getAllUnreadNotificationsforEmp")
	public ResponseEntity<List<NotificationsRequest>> getAllUnreadNotificationsByEmpId(@RequestParam String empId) {
		List<NotificationsRequest> list  = notificationsService.getAllUnreadNotificationsByEmpId(empId);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/getAllUnreadNotificationsForAdmin")
	public ResponseEntity<List<NotificationsRequest>> getAllUnreadNotificationsForAdmin() {
		List<NotificationsRequest> list  = notificationsService.getAllUnreadNotificationsByAdmin();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping("/insertNotification")
	public ResponseEntity<SuccessResponse> insertNotification(@Valid @RequestBody NotificationsRequest notificationsrequest) {
		String message = "";
		Notifications notn = notificationsService.insertNotification(notificationsrequest);
		if( notn != null ) {
			message = "successfully inserted." ;
		} else {
			message = "Unable to insert record currently";
		}
		return ResponseEntity.ok().body(new SuccessResponse(message));
	}
	

	@PutMapping("/updateNotificationsByEmp")
	public ResponseEntity<String> updateNotificaitonByEmp(@RequestParam String empId,
			@Valid @RequestParam String notificationType) throws ResourceNotFoundException {
		notificationsService.updateNotificationForEmp(empId, notificationType);
		return ResponseEntity.ok().body("updated successfully");
	}
	
	@PutMapping("/updateNotificationsByAdmin")
	public ResponseEntity<String> updateNotificaitonByAdmin(@Valid @RequestParam String notificationType) throws ResourceNotFoundException {
		notificationsService.updateNotificationForAdmin(notificationType,ActionEnum.ADMIN.getValue());
		return ResponseEntity.ok().body("updated successfully");
	}
	
}

