package com.cos.todaymeet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.todaymeet.model.Notification;

import jakarta.transaction.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	@Query(value = "SELECT u.time, u.userNumber ,u.meetNumber , u.name, u.imageLink ,u.notiType , u.isProcessed "
			+ "FROM Notification u WHERE u.userNo = :userNo and confirmed = false ORDER BY u.time DESC",nativeQuery=true)
	List<Object[]> selectNotifi(@Param("userNo") int userNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE Notification SET isProcessed = true WHERE userNumber = :userNo AND meetNumber = :meetNo AND notiType=7",nativeQuery=true)
    void isProcessed(int userNo,int meetNo);
}
