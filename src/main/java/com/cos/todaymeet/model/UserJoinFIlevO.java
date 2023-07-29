package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserJoinFIlevO {
	private String email;
	private String password;
	private String username;
	private Integer gender;
	private Timestamp birth;
	private MultipartFile files;
}
