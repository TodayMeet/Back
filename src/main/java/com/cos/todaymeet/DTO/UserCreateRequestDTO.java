package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

import com.cos.todaymeet.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDTO {
	private String username;
	private Integer gender;
	private Timestamp birth;
	
	@Builder
	public UserCreateRequestDTO(String username, Integer gender,Timestamp birth) {
		this.birth=birth;
		this.username=username;
		this.gender=gender;
	}
	public User toEntity() {
		return User.builder()
				.username(username)
				.birth(birth)
				.gender(gender)
				.build();
	}

}
