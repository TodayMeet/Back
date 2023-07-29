package com.cos.todaymeet.DTO;

public class FQADto {
	private String question;
	private String answer;
	
	public void FQADto(String Question, String Answer) {
		this.answer=Answer;
		this.question=Question;
	}
	 public String getQuestion() {
	        return question;
	    }

	    public void setQuestion(String Q) {
	        this.question = Q;
	    }

	    public String getAnswer() {
	        return answer;
	    }

	    public void setAnswer(String A) {
	        this.answer = A;
	    }
}
