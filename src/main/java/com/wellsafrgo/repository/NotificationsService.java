package com.wellsafrgo.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wells.constants.ActionEnum;
import com.wells.constants.NotificationsStatusEnum;
import com.wellsafrgo.model.Notifications;
import com.wellsafrgo.model.NotificationsRequest;

@Service
public class NotificationsService {

	@Autowired
	NotificationsRepository repository;
	
	
	/**
	 * retrieves all notifications from table.
	 * @return
	 */
	public List<NotificationsRequest> getAllNotifications() {
		List<Notifications> notificatiosnlst = repository.findAll();
		List<NotificationsRequest> dataList = notificatiosnlst.stream().map(item -> {
			NotificationsRequest data = new NotificationsRequest();
			data.setEmpId(item.getEmpId());
			data.setMessage(item.getMessage());
			data.setStatus(item.getStatus());
			data.setTypeofNotification(item.getTypeofNotification());
			return data;
		}).collect(Collectors.toList());
		return dataList;
	}
	
	/** 
	 * Find unread notifications 
	 * @return
	 */
	public List<NotificationsRequest> getAllUnreadNotifications() {
		List<Notifications> notificatiosnlst = repository.findAllUnread(NotificationsStatusEnum.UNREAD.getValue());
		List<NotificationsRequest> dataList = notificatiosnlst.stream().map(item -> {
			NotificationsRequest data = new NotificationsRequest();
			data.setEmpId(item.getEmpId());
			data.setMessage(item.getMessage());
			data.setStatus(item.getStatus());
			data.setTypeofNotification(item.getTypeofNotification());
			return data;
		}).collect(Collectors.toList());
		return dataList;
	}
	
	/** 
	 * Find unread notificaitons by Emp 
	 * @param empId
	 * @return
	 */
	
	public List<NotificationsRequest> getAllUnreadNotificationsByEmpId(String empId) {
		List<Notifications> notificatiosnlst = repository.findAllbyEmpId(NotificationsStatusEnum.UNREAD.getValue(), empId);
		List<NotificationsRequest> dataList = notificatiosnlst.stream().map(item -> {
			NotificationsRequest data = new NotificationsRequest();
			data.setEmpId(item.getEmpId());
			data.setMessage(item.getMessage());
			data.setStatus(item.getStatus());
			data.setTypeofNotification(item.getTypeofNotification());
			return data;
		}).collect(Collectors.toList());
		return dataList;
	}
	
	public List<NotificationsRequest> getAllUnreadNotificationsByAdmin() {
		List<Notifications> notificatiosnlst = repository.findAllbyAdmin(NotificationsStatusEnum.UNREAD.getValue(), ActionEnum.ADMIN.getValue());
		List<NotificationsRequest> dataList = notificatiosnlst.stream().map(item -> {
			NotificationsRequest data = new NotificationsRequest();
			data.setEmpId(item.getEmpId());
			data.setMessage(item.getMessage());
			data.setStatus(item.getStatus());
			data.setTypeofNotification(item.getTypeofNotification());
			data.setAction(item.getAction());
			return data;
		}).collect(Collectors.toList());
		return dataList;
	}
	
	public Notifications insertNotification(NotificationsRequest request) {
		Notifications notifications = new Notifications();
		notifications.setEmpId(request.getEmpId());
		notifications.setMessage(request.getMessage());
		notifications.setStatus(request.getStatus());
		notifications.setTypeofNotification(request.getTypeofNotification());
		notifications.setAction(request.getAction());
		return repository.save(notifications);
	}
	
	public void updateNotificationForEmp(String empId, String notificationType) {
		Notifications record = repository.getbyEmpId(NotificationsStatusEnum.UNREAD.getValue(), empId, notificationType );
		if(record != null ) {
			record.setStatus(NotificationsStatusEnum.READ.getValue());
			repository.save(record);
		}
	}
	
	public void updateNotificationForAdmin(String notificationType, String action) {
		Notifications record = repository.getbyEmpId(NotificationsStatusEnum.UNREAD.getValue(), ActionEnum.ADMIN.getValue(), notificationType );
		if(record != null ) {
			record.setStatus(NotificationsStatusEnum.READ.getValue());
			repository.save(record);
		}
	}
	
}
