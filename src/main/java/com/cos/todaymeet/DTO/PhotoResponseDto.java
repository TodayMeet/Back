package com.cos.todaymeet.DTO;

import com.cos.todaymeet.model.Photo;

import lombok.Getter;

@Getter
public class PhotoResponseDto {
	private Long fileId;  // 파일 id

    public PhotoResponseDto(Photo entity){
        this.fileId = entity.getId();
    }
}
