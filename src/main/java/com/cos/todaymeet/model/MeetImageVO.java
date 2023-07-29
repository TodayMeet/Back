package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MeetImageVO {
	//DONE : 입력받을 때 form-data 형식
	//TODO : meet 추가 : peopleNum, timeClosed, personClosed, commentNum 입력받지 않도록 수정
	private int meetNo;

	private String categoryname;
	
	private String address;
	private String lat;
	private String lon;
	private Timestamp time;
	private boolean age;
	private Integer peopleLimit;
	private Integer fee;
	private String title;
	private String content;
	private boolean approval;
	//private Integer peopleNum;
	//private boolean timeClosed;
	//private boolean personClosed;
	//private Integer commentNum;
	private List<MultipartFile> files;
	private Integer userNo;
	
}
