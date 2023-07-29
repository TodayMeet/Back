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
public class Reports {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int reportNo;
		
		private Integer type;
		
		@ManyToOne
		@JoinColumn(name = "meetCommentNo")
		private MeetComment meetcomment;
		
		private Integer status=1;
		private String comment;
		
		@ManyToOne
		@JoinColumn(name = "userNo")
		private User user;//신고한사람
		
		@CreationTimestamp
		private Timestamp reportTime;
		
		private Timestamp processTime;
}
