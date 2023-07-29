package com.cos.todaymeet.DTO;
public class UserTokenDto {
    private int userNo;
    private String token;

    public UserTokenDto() {
        // 기본 생성자
    }

    public UserTokenDto(int userNo, String token) {
        this.userNo = userNo;
        this.token = token;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}