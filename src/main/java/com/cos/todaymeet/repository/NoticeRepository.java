package com.cos.todaymeet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.todaymeet.model.Notice;

public interface NoticeRepository extends JpaRepository<Notice,Integer>{
	@Query(value = "SELECT n.title, n.image, n.time, n.noticeNo from Notice  n where n.isDeleted = false;", nativeQuery = true)
	 List<Object[]> findNoticeListAll();
	
	 @Query(value = "SELECT n.title, n.content, n.time FROM Notice n WHERE n.noticeNo = :noticeNo", nativeQuery = true)
	 List<Object[]> findNoticeListByNoticeNo(@Param("noticeNo") int noticeNo);
	 

	//Notice findByNoticeNo(int noticeNo);
}
