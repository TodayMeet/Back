package com.cos.todaymeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cos.todaymeet.model.Block;

public interface BlockRepository extends JpaRepository<Block,Long>{
	@Transactional
	void deleteByBlockingUserNoAndBlockedUserNo(Integer blockingUserNo , Integer blockedUserNo);
	
	@Query(value = " SELECT blockedUserNo from Block WHERE blockingUserNo = "
			+ ":blockingUserNo ", nativeQuery = true)
	List<Integer> selectBlockedNo(int blockingUserNo);
	
	@Query(value=" SELECT COUNT(b.blockNo) FROM Block b WHERE b.blockingUserNo = :blockingUserNo AND b.blockedUserNo = :blockedUserNo ",nativeQuery=true)
	Integer isBlock(int blockingUserNo, int blockedUserNo);
}
