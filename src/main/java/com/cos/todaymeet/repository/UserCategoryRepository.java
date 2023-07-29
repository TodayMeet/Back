package com.cos.todaymeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.model.UserCategory;
@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer>{
	List<UserCategory> findByUserNo(int userNo);
}
