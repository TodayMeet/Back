package com.cos.todaymeet.DTO;

import com.cos.todaymeet.model.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeCreateDTO {
	
	private String title;
	private String content;
	private boolean isDeleted;
	
	@Builder
	public NoticeCreateDTO(String title, String content, boolean isDeleted) {
		this.title=title;
		this.content=content;
		this.isDeleted=isDeleted;
	}
	public Notice toEntity() {
		return Notice.builder()
				.content(content)
				.title(title)
				.isDeleted(isDeleted)
				.build();
	}
}
