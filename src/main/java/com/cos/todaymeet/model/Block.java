package com.cos.todaymeet.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Block {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blockNo;

	private Integer blockedUserNo; //차단당한 사용자

	private Integer blockingUserNo; // 차단을 수행한 사용자
	
	@CreationTimestamp
	private Timestamp blockTime;
}


		