package com.wellsafrgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wellsafrgo.model.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long>{
	
	@Query("select c from Notifications c where c.status = :status ")
	List<Notifications> findAllUnread(@Param("status") String status);
	

	/**
	 * unread notifications for a particular empid
	 * @param status "unread" 
	 * @param action "empId"
	 * @return
	 */
	@Query("select c from Notifications c where c.status = :status and c.action = :action ")
	List<Notifications> findAllbyEmpId(@Param("status") String status, @Param("action") String action );
	
	/**
	 * unread notifications for an Admin
	 * @param status "unread" 
	 * @param action "Admin
	 * 	 * @return
	 */
	@Query("select c from Notifications c where c.status = :status  and c.action = :action")
	List<Notifications> findAllbyAdmin(@Param("status") String status, @Param("action") String action );
	
	/**
	 * unread notifications for an employee/Admin for a notificationype
	 * @param status "unread" 
	 * @param action "Admin
	 * 	 * @return
	 */
	@Query("select c from Notifications c where c.status = :status  and c.action = :action and c.typeofNotification = :type")
	Notifications getbyEmpId(@Param("status") String status, @Param("action") String action, @Param("type") String type );

	
	/**
	 * unread notifications for an employee/Admin for a notificationype
	 * @param status "unread" 
	 * @param action "Admin
	 * 	 * @return
	 */
	@Query("select c from Notifications c where c.status = :status  and c.action = :action and c.typeofNotification = :type")
	Notifications getbyAdmin(@Param("status") String status, @Param("action") String action, @Param("type") String type );

}
