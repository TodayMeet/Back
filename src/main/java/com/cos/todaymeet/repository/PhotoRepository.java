package com.cos.todaymeet.repository;

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
public interface PhotoRepository extends JpaRepository<Photo, Long>{

	//List<Photo> findAllByUserNo(int boardId);
//
//	@Query("SELECT * FROM file u WHERE u.userNo = :userNo")
//	List<Object[]> findAllByUserNo1(@Param("userNo") int userNo);
//	
	//List<Photo> findAllByUserNo(int userNo);

	@Transactional
    @Query(value="SELECT * from file WHERE meetNo = :meetNo",nativeQuery=true)
	List<Photo> testall(int meetNo);
	
	@Transactional
    @Query(value="SELECT file_id from file WHERE userNo = :userNo",nativeQuery=true)
	Long test(int userNo);
	
	@Transactional
    @Query(value="SELECT * from file WHERE userNo = :userNo",nativeQuery=true)
	List<Photo> gUserNo(int userNo);
	
	@Transactional
    @Query(value="SELECT * from file WHERE meetNo = :meetNo",nativeQuery=true)
	List<MeetPhoto> selectMeetNo(int meetNo);
	
	@Transactional
    @Modifying
    @Query("DELETE FROM Photo mp WHERE mp.id = :photoId")
    void deleteUserPhotoByFileId(@Param("photoId") Long photoId);
}