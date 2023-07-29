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
public class Category {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryNo;
	private String name;
	Category(String name){
		this.name = name;
	}
}
