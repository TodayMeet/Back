package com.cos.todaymeet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.todaymeet.model.Reports;

public interface ReportsRepository extends JpaRepository<Reports,Long>{

	Reports findByReportNo(int reportNo);
	
}
