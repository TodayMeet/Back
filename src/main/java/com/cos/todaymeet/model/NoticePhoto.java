package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;




@Builder
@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "noticefile")
public class NoticePhoto extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "noticeNo")
    private Notice notice;
    
    @Column(nullable = false)
    private String origFileName;  // 파일 원본명

    @Column(nullable = false)
    private String filePath;  // 파일 저장 경로

    private Long fileSize;

    @Builder
    public NoticePhoto(String origFileName, String filePath, Long fileSize){
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

 // Board 정보 저장
    public void setNotice(Notice notice){
        this.notice = notice;

	// 게시글에 현재 파일이 존재하지 않는다면
        if(!notice.getPhoto().contains(this))
            // 파일 추가
        	notice.getPhoto().add(this);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, origFileName, filePath, fileSize);
    }

}