package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.List;

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
public class FAQ {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int FAQNo;
	private String question;
	private String answer;
	
	
	 // Getter and Setter methods
    public int getFAQNo() {
        return FAQNo;
    }

    public void setFAQNo(int FAQNo) {
        this.FAQNo = FAQNo;
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
