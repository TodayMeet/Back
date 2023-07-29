package com.cos.todaymeet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.todaymeet.model.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry,Integer> {

}
