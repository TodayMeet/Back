package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

import com.cos.todaymeet.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinCreateRequestDTO {
	private String email;
	private String password;
	private String username;
	private Integer gender;
	private Timestamp birth;
	
	@Builder
	public UserJoinCreateRequestDTO(String email, String password,String username, Integer gender,Timestamp birth) {
		this.birth=birth;
		this.username=username;
		this.gender=gender;
		this.email=email;
		this.password=password;
	}
	public User toEntity() {
		return User.builder()
				.email(email)
				.password(password)
				.username(username)
				.birth(birth)
				.gender(gender)
				.build();
	}

}
