package com.cos.todaymeet.DTO;

import java.sql.Timestamp;
import java.util.List;

import com.cos.todaymeet.model.Category;
import com.cos.todaymeet.model.Meet;
import com.cos.todaymeet.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MeetCreateRequestDTO {

	private Category category;
	
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
	private Integer peopleNum;
	private boolean timeClosed;
	private boolean personClosed;
	private Integer commentNum;
	private User user;
	private String meetImage;
	
	@Builder
	public MeetCreateRequestDTO(User user, String title ,
			Category category, Timestamp time, 
			boolean age,
			String address,  Integer commentNum,
            Integer peopleLimit, Integer peopleNum, String lat,
            String lon, boolean personClosed, Integer fee,
            String content,
            boolean approval, boolean timeClosed, String meetImage) {
	    this.user = user;
	    this.title = title;
	    this.category = category;
	    this.time = time;
	    this.age = age;
	    this.address = address;
	    this.commentNum = commentNum;
	    this.peopleLimit = peopleLimit;
	    this.peopleNum = peopleNum;
	    this.lat = lat;
	    this.lon = lon;
	    this.personClosed = personClosed;
	    this.fee = fee;
	    this.content = content;
	    this.approval = approval;
	    this.timeClosed = timeClosed;
	    this.meetImage=meetImage;
	}
	public Meet toEntity() {
		return Meet.builder()
				.category(category)
				.address(address)
				.approval(approval)
				.age(age)
				.commentNum(commentNum)
				.content(content)
				.fee(fee)
				.lat(lat)
				.lon(lon)
				.peopleLimit(peopleLimit)
				.peopleNum(peopleNum)
				.personClosed(personClosed)
				.time(time)
				.title(title)
				.user(user)
				.timeClosed(timeClosed)
				.meetImage(meetImage)
				.build();
	}
	
}
