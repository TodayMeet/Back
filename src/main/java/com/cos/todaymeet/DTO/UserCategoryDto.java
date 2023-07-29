package com.cos.todaymeet.DTO;

public class UserCategoryDto {
	private int userNo;
	private String categoryname;
	public UserCategoryDto(int userNo, String categoryname) {
        this.userNo = userNo;
        this.categoryname = categoryname;
    }
    
    public int getUserNo() {
        return userNo;
    }
    
    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
    
    public String getCategoryname() {
        return categoryname;
    }
    
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
