package com.cos.todaymeet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int followNo;
	private Integer followerNo;
	private Integer followeeNo;
	
	public Follow(Integer followeeNo,Integer followerNo) {
		this.followeeNo=followeeNo;
		this.followerNo=followerNo;
	}
}
