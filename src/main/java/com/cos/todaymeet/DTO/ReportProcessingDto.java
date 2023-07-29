package com.cos.todaymeet.DTO;

public class ReportProcessingDto {
	private int reportNo;
	private Integer status;
	private String comment;
	  public int getReportNo() {
	        return reportNo;
	    }

	    public void setReportNo(int reportNo) {
	        this.reportNo = reportNo;
	    }

	    public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public String getComment() {
	        return comment;
	    }

	    public void setComment(String comment) {
	        this.comment = comment;
	    }
}
