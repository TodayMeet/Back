package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeNo;
    private String image;
    private String title;
    private String content;
    @CreationTimestamp
    private Timestamp time;
    private boolean isDeleted;
    @OneToMany(
    	    mappedBy = "notice",
    	    cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    	    orphanRemoval = true
    	)
    	private List<NoticePhoto> photo = new ArrayList<>();

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addPhoto(NoticePhoto photo) {
        this.photo.add(photo);

        if (photo.getNotice() != this)
            photo.setNotice(this);
    }

    @Builder
    public Notice(String image, String title, String content, Timestamp time, boolean isDeleted) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.time = time;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(int noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
