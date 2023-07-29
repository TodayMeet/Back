package com.cos.todaymeet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.model.NoticePhoto;
@Repository
public interface NoticePhotoRepository extends JpaRepository<NoticePhoto,Long>{
	@Query(value = "SELECT file_id FROM noticefile n WHERE n.noticeNo = :noticeNo", nativeQuery = true)
	 List<String> findNoticeFileList(@Param("noticeNo") int noticeNo);
}
