package com.cos.todaymeet.DTO;

import com.cos.todaymeet.model.MeetPhoto;

import lombok.Getter;

@Getter
public class MeetPhotoResponseDto {
	private Long fileId;  // 파일 id

    public MeetPhotoResponseDto(MeetPhoto entity){
        this.fileId = entity.getId();
    }
}
