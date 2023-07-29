package com.cos.todaymeet.DTO;

import com.cos.todaymeet.model.Photo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class PhotoDTO {

    private String origFileName;  // 파일 원본명

    private String filePath;  // 파일 저장 경로

    private Long fileSize;

    @Builder
    public PhotoDTO(String origFileName, String filePath, Long fileSize) {
    	this.origFileName=origFileName;
    	this.filePath=filePath;
    	this.fileSize=fileSize;
    }
    public Photo toEntity() {
		return Photo.builder()
				.origFileName(origFileName)
				.filePath(filePath)
				.fileSize(fileSize)
				.build();
    }
}
