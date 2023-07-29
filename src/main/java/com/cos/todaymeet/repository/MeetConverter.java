package com.cos.todaymeet.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cos.todaymeet.DTO.MeetListDTO;
import com.cos.todaymeet.model.MeetComment;
import com.cos.todaymeet.model.User;

@Component
public class MeetConverter {

	// 리스트 화면에 띄울 정보들을 하나로 모아서 return 한다. 대부분 리스트 api에서 사용된다.
	public List<MeetListDTO> convertToObjectList(List<Object[]> objectList) {
	    List<MeetListDTO> meetList = new ArrayList<>();
	    
	    for (Object[] objects : objectList) {
	        
	    	String categoryName = (String) objects[0];
	        String title = (String) objects[1];
	        Timestamp time = (Timestamp) objects[2];
	        int meetNo = (int) objects[3];
	        int userNo = (int) objects[4];
	        String userProfileImage = (String) objects[5];
	        String userName = (String) objects[6];
	        Integer commentNum = (Integer) objects[7];
	        Integer peopleLimit = (Integer) objects[8];
	        Boolean personClosed = (Boolean) objects[9];
	        Integer peopleNum = (Integer) objects[10];
	        //String location = (String) objects[11];
	        String address = (String) objects[11];
	        String lon = (String)objects[12];
	        String lat = (String)objects[13];
	        String meetImage = (String)objects[14];

	        MeetListDTO meetDTO = new MeetListDTO(categoryName, title, time, 
	        		meetNo, userNo, userProfileImage, userName, commentNum,
	        		peopleLimit, personClosed, peopleNum, lon,lat, address,meetImage);
	        meetList.add(meetDTO);
	    }
	    
	    return meetList;
	}

//	public MeetDetailDTO convertToDetail(Object[] objects,
//			List<MeetComment> meetcomments , List<UserDTO> meetuserList) {
//	    //MeetDetailDTO meetList = new MeetDetailDTO()
//	    int meetNo = (int) objects[0];
//    	String categoryName = (String) objects[1];
//    	Timestamp time = (Timestamp) objects[2];
//    	String title = (String) objects[3];
//    	boolean age = (boolean) objects[4];
//    	int userNo = (int) objects[5];
//    	String userProfileImage = (String) objects[6];
//    	String username = (String) objects[7];
//    	String address = (String) objects[8];
//    	String meetImage = (String) objects[9];
//    	Integer commentNum = (Integer) objects[10];
//    	Integer peopleLimit = (Integer) objects[11];
//    	Integer peopleNum = (Integer) objects[12];
//    	String lat = (String) objects[13];
//    	String lon = (String) objects[20];
//    	boolean personClosed = (boolean) objects[14];
//    	Integer fee = (Integer) objects[15];
//    	String content = (String) objects[16];
//    	boolean approval = (boolean) objects[17];
//    	boolean timeClosed = (boolean) objects[18];
//    	UserDTO hostUser = (UserDTO) objects[19];
//    	List<MeetComment> comments = meetcomments;
//        List<UserDTO> userList = meetuserList;
//        
//        MeetDetailDTO meetDTO = new MeetDetailDTO(meetNo, categoryName, time, title, age, userNo,
//                userProfileImage, username, address, meetImage, commentNum,
//                peopleLimit, peopleNum, lat, lon, personClosed, fee, content,
//                approval, timeClosed, hostUser, comments, userList);
//        return meetDTO;
//        }
}
