package com.cos.todaymeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cos.todaymeet.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer>{
	@Transactional
	void deleteByFollowerNoAndFolloweeNo(Integer followerNo, Integer followeeNo);

	@Query(value = "SELECT followerNo from Follow WHERE followeeNo = :userNo",nativeQuery=true)
	List<Integer> selectFolloweeNo(int userNo);
	
	@Query(value = "SELECT followeeNo from Follow WHERE followerNo = :userNo",nativeQuery=true)
	List<Integer> selectFollowerNo(int userNo);
	
	@Query(value=" SELECT COUNT(followNo) FROM Follow f WHERE f.followerNo = :followNo AND f.followeeNo = :followeeNo ",nativeQuery=true)
	Integer isFollow(int followNo, int followeeNo);
}
