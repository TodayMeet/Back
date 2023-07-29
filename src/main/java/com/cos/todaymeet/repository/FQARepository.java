package com.cos.todaymeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.todaymeet.model.FAQ;

public interface FQARepository extends JpaRepository<FAQ,Integer>{
	@Query(value="SELECT * from FAQ",nativeQuery=true)
	List<FAQ> selectFAQList();
}
