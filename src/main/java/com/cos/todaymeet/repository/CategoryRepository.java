package com.cos.todaymeet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findByName(String name);
	Category findByCategoryNo(int categoryNo);
}
