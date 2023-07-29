package com.cos.todaymeet.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.model.MeetPhoto;
import com.cos.todaymeet.model.Photo;

import jakarta.transaction.Transactional;
@Repository
public interface MeetPhotoRepository extends JpaRepository<MeetPhoto, Long>{
	@Transactional
    @Modifying
    @Query("DELETE FROM MeetPhoto mp WHERE mp.id = :photoId")
    void deleteMeetPhotoByFileId(@Param("photoId") Long photoId);

	@Transactional
    @Query(value="SELECT file_id from meetfile WHERE origFileName = :origFileName and filePath =:filePath LIMIT 1",nativeQuery=true)
	Long selectMeetId1(String origFileName,String filePath);

	@Transactional
    @Query(value="SELECT file_id from meetfile WHERE meetNo = :meetNo and origFileName = :origFileName",nativeQuery=true)
	String findmeetImage(int meetNo,String origFileName);
	
	@Transactional
    @Query(value="SELECT origFileName from meetfile WHERE meetNo = :meetNo",nativeQuery=true)
	List<String> findOriginName(int meetNo);
	List<MeetPhoto> findAllByMeet(int meetNo);
//
//	@Query("SELECT * FROM file u WHERE u.userNo = :userNo")
//	List<Object[]> findAllByUserNo1(@Param("userNo") int userNo);
//	
	//List<Photo> findAllByUserNo(int userNo);

	@Transactional
    @Query(value="SELECT * from meetfile WHERE meetNo = :meetNo",nativeQuery=true)
	List<Photo> testall(int meetNo);
	
	@Transactional
    @Query(value="SELECT file_id from meetfile WHERE userNo = :userNo",nativeQuery=true)
	Long test(int userNo);
	
	@Transactional
    @Query(value="SELECT * from meetfile WHERE meetNo = :meetNo",nativeQuery=true)
	List<MeetPhoto> selectMeetNo(int meetNo);
	
	// 모임의 이미지들의 id를 return 한다.
	@Transactional
    @Query(value="SELECT file_id from meetfile WHERE meetNo = :meetNo",nativeQuery=true)
	List<String> selectIDMeetNo(int meetNo);
	
	
	@Transactional
    @Query(value="SELECT file_id from meetfile WHERE meetNo = :meetNo",nativeQuery=true)
	List<String> selectOriginMeetNo(int meetNo);
	
	//void deleteMeetPhoto(Long id);
}