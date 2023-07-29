package com.cos.todaymeet.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.model.User;

import jakarta.transaction.Transactional;


/**
 * 
 * @author cos
 * JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음.
 * JPA는 method names 전략을 가짐. README.md 사진 참고
 */

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	// SELECT * FROM User WHERE username = ?1
	User findByUsername(String username);
	
	@Query(value = "SELECT u.token FROM User u WHERE u.userNo = :userNo",nativeQuery = true)
	String selectToken(int userNo);
	
	User findByUserNo(int userNo);
	@Query(value="SELECT u.userNo, u.profileImage, u.username FROM  User  u  WHERE  u.userNo = :userNo",nativeQuery=true)
	List<Object[]> tttt(int userNo);
	
	@Query(value="SELECT u.email FROM  User  u  WHERE  u.phoneNumber = :phoneNumber",nativeQuery=true)
	String findEmail(String phoneNumber);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET isDeleted = true WHERE userNo = :userNo",nativeQuery=true)
    void deleteUser( int userNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET token = :token WHERE userNo = :userNo",nativeQuery=true)
    void UpdateToken(int userNo,String token);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET password = :password WHERE userNo = :userNo",nativeQuery=true)
    void updatePassword(String password, int userNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET followNum =followNum+1 WHERE userNo = :userNo",nativeQuery=true)
    void updateFollowNumPlus( int userNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET followNum =followNum-1 WHERE userNo = :userNo",nativeQuery=true)
    void updateFollowNumMinus( int userNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET followeeNum =followeeNum+1 WHERE userNo = :userNo",nativeQuery=true)
    void updateFolloweeNumPlus( int userNo);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET followeeNum =followeeNum-1 WHERE userNo = :userNo",nativeQuery=true)
    void updateFolloweeNumMinus( int userNo);
	
	
	@Query("SELECT u.userNo, u.profileImage,"
			+ "u.username, u.birth, u.gender, u.followNum, u.followeeNum "
			+ "FROM User u WHERE u.userNo = :userNo")
	List<Object[]> selectUserProfile(@Param("userNo") int userNo);
	
	 @Query("SELECT u.birth FROM User u WHERE u.userNo = :userNo")
	 Timestamp selectBirth(@Param("userNo") int userNo);
	
	//@Query("SELECT * FROM User u WHERE u.userNo = :userNo")
	//User findUseDTOByUserNo(int userNo);

    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String selectPassword(@Param("email") String email);

    @Query("SELECT u.userNo FROM User u WHERE u.email = :email")
    int selectUserNo(@Param("email") String email);
	// SELECT * FROM user WHERE provider = ?1 and providerId = ?2
	Optional<User> findByProviderAndProviderId(String provider, String providerId);

	// 
	boolean existsByPhoneNumber(String phoneNumber);
	
	boolean existsByEmail(String email);
	
	boolean existsByUsername(String username);
//	@Transactional
//	public static void updatePhoneNumber(String phoneNumber,Integer userNo) {
//		//Optional<User> byUserNo = findByUserNo(userNo);
//	}
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE User SET phoneNumber = :phoneNumber WHERE userNo = :userNo",nativeQuery=true)
    void updatePhoneNumber(String phoneNumber, int userNo);
}