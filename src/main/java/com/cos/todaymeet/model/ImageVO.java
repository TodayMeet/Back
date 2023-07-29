package com.cos.todaymeet.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

public class ImageVO {

    private Integer id;
    private String mimetype;
    private String original_name;
    private byte[] data;
    private String created;
}