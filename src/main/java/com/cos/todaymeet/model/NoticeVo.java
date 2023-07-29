package com.cos.todaymeet.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeVo {
	private String title;
	private String content;
	private List<MultipartFile> files;
	private boolean isDeleted;
}
