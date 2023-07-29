package com.cos.todaymeet.model;

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
public class MeetImage {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetImageNo;
	private String image;
	@ManyToOne
	@JoinColumn(name = "meetNo")
	private Meet meet;
	private String thumbnail;
	
	public MeetImage(String image,Meet meetNo, String thumbnail) {
		this.image=image;
		this.meet=meetNo;
		this.thumbnail=thumbnail;
	}
}
