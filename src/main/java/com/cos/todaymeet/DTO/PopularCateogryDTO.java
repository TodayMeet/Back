package com.cos.todaymeet.DTO;

public class PopularCateogryDTO {
	private String name;
	private Long count;

	public PopularCateogryDTO(String name, Long count) {
		this.name = name;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
