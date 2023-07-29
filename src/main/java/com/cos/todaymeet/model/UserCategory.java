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
public class UserCategory {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userCategoryNo;
	private int userNo;
	private int categoryNo;
	
	public UserCategory(int userNo, int categoryNo) {
		this.categoryNo=categoryNo;
		this.userNo=userNo;
	}
}
