package com.cos.todaymeet.repository;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.MeetSort;
import com.cos.todaymeet.DTO.MeetListDTO;
import com.cos.todaymeet.model.Meet;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.transaction.Transactional;
/**
 * 
 * @author cos
 * JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음.
 * JPA는 method names 전략을 가짐. README.md 사진 참고
 */
//************MeetListDTO*********************//
@SqlResultSetMapping(
    name = "MeetListDTOMapping",
    classes = @ConstructorResult(
        targetClass = MeetListDTO.class,
        columns = {
            @ColumnResult(name = "categoryName", type = String.class),
            @ColumnResult(name = "title", type = String.class),
            @ColumnResult(name = "time", type = Timestamp.class),
            @ColumnResult(name = "meetNo", type = Long.class),
            @ColumnResult(name = "userNo", type = Long.class),
            @ColumnResult(name = "userProfileImage", type = String.class),
            @ColumnResult(name = "username", type = String.class),
            @ColumnResult(name = "commentNum", type = Long.class),
            @ColumnResult(name = "peopleLimit", type = Integer.class),
            @ColumnResult(name = "personClosed", type = Boolean.class),
            @ColumnResult(name = "peopleNum", type = Integer.class),
            @ColumnResult(name = "location", type = String.class),
            @ColumnResult(name = "address", type = String.class)
        }
    )
)
//************MeetDetailDTO*********************//
@SqlResultSetMapping(
	    name = "MeetDetailDTOMapping",
	    classes = @ConstructorResult(
	        targetClass = MeetListDTO.class,
	        columns = {
	            @ColumnResult(name = "categoryName", type = String.class),
	            @ColumnResult(name = "title", type = String.class),
	            @ColumnResult(name = "time", type = Timestamp.class),
	            @ColumnResult(name = "meetNo", type = Long.class),
	            @ColumnResult(name = "userNo", type = Long.class),
	            @ColumnResult(name = "userProfileImage", type = String.class),
	            @ColumnResult(name = "username", type = String.class),
	            @ColumnResult(name = "commentNum", type = Long.class),
	            @ColumnResult(name = "peopleLimit", type = Integer.class),
	            @ColumnResult(name = "personClosed", type = Boolean.class),
	            @ColumnResult(name = "peopleNum", type = Integer.class),
	            @ColumnResult(name = "location", type = String.class),
	            @ColumnResult(name = "address", type = String.class)
	        }
	    )
	)

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
@Repository
public interface MeetRepository extends JpaRepository<Meet, Integer>{
	
	
	
	// SELECT * FROM user WHERE username = ?1
	Meet findByMeetNo(int meetNo);
	
	@Query(value = "SELECT m.meetNo, c.name, m.lat,m.lon,m.time FROM Meet m "
			+ "INNER JOIN Category c ON c.categoryNo = m.categoryNo "
			+ "WHERE m.address LIKE %:address%  and m.time > CURRENT_TIMESTAMP()"
			, nativeQuery = true)
	List<Object[]> selectPinByAddress(@Param("address") String address);

	
//	@Query(
//			value = "SELECT title FROM Meet m WHERE m.meetNo =:meetNo")
//	String findTitleByMeetNo(int meetNo);
	
//	@Query(value = "SELECT c.name,m.title,m.time,m.meetNo,m.userNo,m.address,"
//			+ "m.commentNum,m.peopleLimit,m.location,m.personClosed From Meet m Inner join Category c "
//			+ "WHERE m.meetNo =:meetNo and c.categoryNo = m.categoryNo")
//	String selectMeetByMeetNo(int meetNo);
	
//	@Query(
//	value = "SELECT c.name as categoryName,"
//	+ " m.title as title , m.time as time , m.meetNo as meetNo , "
//	+ " m.userNo as userNo,u.profileImage as userProfileImage, "
//	+ " u.username as userName,m.commentNum as commentNum,"
//	+ " m.peopleLimit as peopleLimit , m.personClosed as personClosed,"
//	+ " m.peopleNum as peopleLimit , m.location as location, "
//	+ " m.address as address"
//	+ " From Meet m inner join category c on m.categoryNo = c.categoryNo "
//	+ " inner join user u on m.userNo = u.userNo "
//	+ "  where m.address LIKE %:address%", nativeQuery = false)
//	List<MeetListDTO> findByAddressLike1(@Param("address") String address);
//	
//	@Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time, m.meetNo as meetNo, m.userNo as userNo, u.profileImage as userProfileImage, u.username as username, m.commentNum as commentNum, m.peopleLimit as peopleLimit, m.personClosed as personClosed, m.peopleNum as peopleLimit, m.location as location, m.address as address "
//			+ "FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
//			+ "WHERE m.address LIKE %:address%", nativeQuery = true)
//	List<MeetListDTO> findByAddressLike1(@Param("address") String address);
//	%:address% AND m.categoryNo IN :categoryNos AND m.time > CURRENT_TIMESTAMP() "

	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE meet SET title = :phoneNumber WHERE meetNo = :userNo",nativeQuery=true)
    void updatePhoneNumber(String phoneNumber, int userNo);
   
    String findTitleByMeetNo(int meetNo);
    //String selectMeetByMeetNo(int meetNo);
    
    @Transactional
    @Query(value="SELECT userNo from Meet WHERE meetNo = :meetNo",nativeQuery=true)
	int findUserNoByMeetNo(int meetNo);
//	@PersistenceContext
//	EntityManager getEntityManager();
//	
	//**************모임 리스트**************************************//
	 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
	 		+ " m.meetNo as meetNo, m.userNo as userNo,"
	 		+ " u.profileImage as userProfileImage, u.username as username,"
	 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
	 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
	 		+ "  m.address as address ,m.lon as lon, m.lat as lat,m.meetImage as meetImage "
		        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
		        + "WHERE m.time > CURRENT_TIMESTAMP() "
		        + "ORDER BY CASE "
		        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
		        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
		        + " END DESC,"
		        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
	 List<Object[]> findByAddressLikeAll(@Param("sort_value") String sort_value);
	 default List<MeetListDTO> findMeetListByAll(String sortName) {
//		 System.out.println(sortName);
//		 org.json.JSONObject json = new org.json.JSONObject(sortName);
//		 String sort = json.getString("sort");
		 String sort = sortName;
		 System.out.println(sort);
		 MeetSort meetSort;
		 String sort_value = "";
	     if(sort.equals("최신순")) {
	    	 sort_value = "LATEST";
	     }else if (sort.equals("마감임박순")) {
	    	 sort_value = "DEADLINE";
	     }else if(sort.equals("참여높은 순")){
	    	 sort_value = "PARTICIPANTS";
	     }else{
	    	 sort_value = "LATEST";
	     }
	     
	     List<Object[]> objectList = findByAddressLikeAll(sort_value);
	        MeetConverter converter = new MeetConverter();
	        return converter.convertToObjectList(objectList);
	 }
	/******************************************************************************************************************************************************/
	 // 모임 제목 리스트 출력'
	 // categoryname,title,time,meetno,userno,profileImage,username,commentNum, peopleLimit,personClosed,peopleNum,address, lon,lat,meetImage(thumbnail)
	 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
	 		+ " m.meetNo as meetNo, m.userNo as userNo,"
	 		+ " u.profileImage as userProfileImage, u.username as username,"
	 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
	 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
	 		+ "  m.address as address ,m.lon as lon, m.lat as lat,m.meetImage as meetImage"
		        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
		        + "WHERE m.address LIKE %:address% AND m.time > CURRENT_TIMESTAMP() "
		        + "ORDER BY CASE "
		        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
		        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
		        + " END DESC,"
		        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
	 Page<Object[]> findByAddressLike1(@Param("address") String address,@Param("sort_value") String sort_value,Pageable pageable);
	 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
		 		+ " m.meetNo as meetNo, m.userNo as userNo,"
		 		+ " u.profileImage as userProfileImage, u.username as username,"
		 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
		 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
		 		+ "  m.address as address ,m.lon as lon, m.lat as lat,m.meetImage as meetImage"
			        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
			        + "WHERE m.address LIKE %:address% AND m.categoryNo IN :categoryNos AND m.time > CURRENT_TIMESTAMP() "
			        + "ORDER BY CASE "
			        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
			        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
			        + " END DESC,"
			        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
		 Page<Object[]> findByAddressLikeCategory(@Param("address") String address,@Param("sort_value") String sort_value,Pageable pageable,@Param("categoryNos") List<Integer> categoryNos);
	 default List<MeetListDTO> findMeetListByAddress(String address,String sortName,int page,List<Integer> categoryNo) {
		 //String sort = sortName;
		 String sort = sortName;
		 MeetSort meetSort;
		 //정렬 이름을 받아서 query에서 쓸 수 있는 값으로 변홚나다.
		 String sort_value = "";
	     if(sort.equals("최신순")) {
	    	 sort_value = "LATEST";
	     }else if (sort.equals("마감임박순")) {
	    	 sort_value = "DEADLINE";
	     }else if(sort.equals("참여높은 순")){
	    	 sort_value = "PARTICIPANTS";
	     }else{
	    	 sort_value = "LATEST";
	     }
	     //address = "정";
	     System.out.println("sort "+sort);
	     // 5개씩 끊어서 출력
	     Pageable pageable = PageRequest.of(page, 5);
	     Page<Object[]> meetPage;
	     List<Object[]> objectList ;
	     System.out.println("address"+address);
	     String categoryQuery="";
	     //전체보기이면
	     if(categoryNo == null) {
			 meetPage = findByAddressLike1(address, sort_value, pageable);
			 objectList = meetPage.getContent();
		 }else {

			 
			  meetPage = findByAddressLikeCategory(address, sort_value, pageable,categoryNo);
		      objectList = meetPage.getContent();
		 }
	     	//받은 데이터를 변환
	        MeetConverter converter = new MeetConverter();
	        return converter.convertToObjectList(objectList);
	 }
	/******************************************************************************************************************************************************/
	 //모임 카테고리 리스트 출력
		 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
			 		+ " m.meetNo as meetNo, m.userNo as userNo,"
			 		+ " u.profileImage as userProfileImage, u.username as username,"
			 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
			 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
			 		+ "  m.address as address ,m.lon as lon, m.lat as lat ,m.meetImage as meetImage "
				        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
				        + "ORDER BY CASE "
				        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
				        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
				        + " END DESC,"
				        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
		 	Page<Object[]> findByCategoryLikeAll(@Param("sort_value") String sort_value,Pageable pageable);
			 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
			 		+ " m.meetNo as meetNo, m.userNo as userNo,"
			 		+ " u.profileImage as userProfileImage, u.username as username,"
			 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
			 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
			 		+ "  m.address as address ,m.lon as lon, m.lat as lat ,m.meetImage as meetImage "
				        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
				        + "where m.categoryNo = :categoryNo "
				        + "ORDER BY CASE "
				        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
				        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
				        + " END DESC,"
				        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
			 Page<Object[]> findByCategoryLike(@Param("sort_value") String sort_value,int categoryNo,Pageable pageable);
			 default List<MeetListDTO> findMeetListByCategory(int categoryNo , String sortName,int page) {
//				 System.out.println(sortName);
//				 org.json.JSONObject json = new org.json.JSONObject(sortName);
//				 String sort = json.getString("sort");
				 String sort = sortName;
				 System.out.println(sort);
				 MeetSort meetSort;
				 String sort_value = "";
			     if(sort.equals("최신순")) {
			    	 sort_value = "LATEST";
			     }else if (sort.equals("마감임박순")) {
			    	 sort_value = "DEADLINE";
			     }else if(sort.equals("참여높은 순")){
			    	 sort_value = "PARTICIPANTS";
			     }else{
			    	 sort_value = "LATEST";
			     }
			     Pageable pageable = PageRequest.of(page, 5);
			     Page<Object[]> meetPage ;
				 List<Object[]> objectList;
				 if(categoryNo == 0) {
					 meetPage = findByCategoryLikeAll( sort_value, pageable);
					 objectList = meetPage.getContent();
				 }else {
					  meetPage = findByCategoryLike( sort_value,categoryNo, pageable);
				      objectList = meetPage.getContent();
				 }

			        MeetConverter converter = new MeetConverter();
			        return converter.convertToObjectList(objectList);
			 }
	/*********************************************************************************************************************************************/
	// 모임 제목 리스트 리턴
	 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
		 		+ " m.meetNo as meetNo, m.userNo as userNo,"
		 		+ " u.profileImage as userProfileImage, u.username as username,"
		 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
		 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
		 		+ "  m.address as address ,m.lon as lon, m.lat as lat ,m.meetImage as meetImage "
			        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
			        + "WHERE m.title LIKE %:title% "
			        + "ORDER BY CASE "
			        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
			        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
			        + " END DESC,"
			        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
	 	 Page<Object[]> findByTitleLikeAll(@Param("title") String title,@Param("sort_value") String sort_value,Pageable pageable);
		 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
		 		+ " m.meetNo as meetNo, m.userNo as userNo,"
		 		+ " u.profileImage as userProfileImage, u.username as username,"
		 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
		 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
		 		+ "  m.address as address ,m.lon as lon, m.lat as lat ,m.meetImage as meetImage "
			        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
			        + "WHERE m.title LIKE %:title% AND m.categoryNo = :categoryNo "
			        + "ORDER BY CASE "
			        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
			        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
			        + " END DESC,"
			        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
		 Page<Object[]> findByTitleLike(@Param("title") String title,@Param("sort_value") String sort_value,int categoryNo,Pageable pageable);
		 default List<MeetListDTO> findMeetListByTitle(String title,int categoryNo , String sortName,int page) {
//			 System.out.println(sortName);
//			 org.json.JSONObject json = new org.json.JSONObject(sortName);
//			 String sort = json.getString("sort");
			 String sort = sortName;
			 System.out.println(sort);
			 MeetSort meetSort;
			 String sort_value = "LATEST";
		     if(sort.equals("최신순")) {
		    	 sort_value = "LATEST";
		     }else if (sort.equals("마감임박순")) {
		    	 sort_value = "DEADLINE";
		     }else if(sort.equals("참여높은 순")){
		    	 sort_value = "PARTICIPANTS";
		     }else{
		    	 sort_value = "LATEST";
		     }
		     Pageable pageable = PageRequest.of(page, 5);
		     Page<Object[]> meetPage ;
			 List<Object[]> objectList;
			 if(categoryNo == 0) {
				 meetPage = findByTitleLikeAll( title,sort_value, pageable);
				 objectList = meetPage.getContent();
			 }else {
				  meetPage = findByTitleLike(title, sort_value,categoryNo, pageable);
			      objectList = meetPage.getContent();
			 }

		        MeetConverter converter = new MeetConverter();
		        return converter.convertToObjectList(objectList);
		 }
		 /********************************************************************************************************************************************/
			//모임 주최 리스트 - 카테고리는 필터링하지 않는다
		 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
			 		+ " m.meetNo as meetNo, m.userNo as userNo,"
			 		+ " u.profileImage as userProfileImage, u.username as username,"
			 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
			 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
			 		+ "  m.address as address ,m.lon as lon, m.lat as lat ,m.meetImage as meetImage "
				        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo INNER JOIN MeetUser mu ON m.meetNo = mu.meetNo"
				        + " WHERE mu.userNo = :userNo "
				        + "ORDER BY CASE "
				        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
				        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
				        + " END DESC,"
				        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
		 	 Page<Object[]> findByUserNoPart(@Param("userNo") int userNo,@Param("sort_value") String sort_value,Pageable pageables);
			 default List<MeetListDTO> findMeetPartListByUserNo(int  userNo, String sort, int page) {
			     
				 MeetSort meetSort;
				 String sort_value = "";
			     if(sort.equals("최신순")) {
			    	 sort_value = "LATEST";
			     }else if (sort.equals("마감임박순")) {
			    	 sort_value = "DEADLINE";
			     }else if(sort.equals("참여높은 순")){
			    	 sort_value = "PARTICIPANTS";
			     }else{
			    	 sort_value = "LATEST";
			     }
			     if(sort.equals("최신순")) {
			    	 sort_value = "m.createTime";
			     }else if (sort.equals("마감임박순")) {
			    	 sort_value = "m.time";
			     }else if(sort.equals("참여높은 순")){
			    	 sort_value = "m.peopleNum";
			     }System.out.println(sort_value);
			     Page<Object[]> meetPage ;
				 List<Object[]> objectList;
			     Pageable pageable = PageRequest.of(page, 5);
			     meetPage = findByUserNoPart(userNo,sort_value,pageable);
			     objectList = meetPage.getContent();
			        MeetConverter converter = new MeetConverter();
			        return converter.convertToObjectList(objectList);
			 }
	 /********************************************************************************************************************************************/
	//모임 참가 리스트 - 카테고리는 필터링하지 않는다
	 @Query(value = "SELECT c.name as categoryName, m.title as title, m.time as time,"
		 		+ " m.meetNo as meetNo, m.userNo as userNo,"
		 		+ " u.profileImage as userProfileImage, u.username as username,"
		 		+ " m.commentNum as commentNum, m.peopleLimit as peopleLimit,"
		 		+ " m.personClosed as personClosed, m.peopleNum as peopleNum,"
		 		+ "  m.address as address ,m.lon as lon, m.lat as lat ,m.meetImage as meetImage "
			        + " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo INNER JOIN User u ON m.userNo = u.userNo "
			        + "WHERE m.userNo = :userNo  "
			        + "ORDER BY CASE "
			        + "WHEN :sort_value = 'PARTICIPANTS' THEN m.peopleNum "
			        + " WHEN :sort_value = 'LATEST' THEN m.createTime"
			        + " END DESC,"
			        + " CASE WHEN :sort_value = 'DEADLINE' THEN m.time END ASC;", nativeQuery = true)
	 	 Page<Object[]> findByUserNo(@Param("userNo") int userNo,@Param("sort_value") String sort_value,Pageable pageables);
		 default List<MeetListDTO> findMeetListByUserNo(int  userNo, String sort, int page) {
		     
			 MeetSort meetSort;
			 String sort_value = "";
		     if(sort.equals("최신순")) {
		    	 sort_value = "LATEST";
		     }else if (sort.equals("마감임박순")) {
		    	 sort_value = "DEADLINE";
		     }else if(sort.equals("참여높은 순")){
		    	 sort_value = "PARTICIPANTS";
		     }else{
		    	 sort_value = "LATEST";
		     }
		     if(sort.equals("최신순")) {
		    	 sort_value = "m.createTime";
		     }else if (sort.equals("마감임박순")) {
		    	 sort_value = "m.time";
		     }else if(sort.equals("참여높은 순")){
		    	 sort_value = "m.peopleNum";
		     }System.out.println(sort_value);
		     Page<Object[]> meetPage ;
			 List<Object[]> objectList;
		     Pageable pageable = PageRequest.of(page, 5);
		     meetPage = findByUserNo(userNo,sort_value,pageable);
		     objectList = meetPage.getContent();
		        MeetConverter converter = new MeetConverter();
		        return converter.convertToObjectList(objectList);
		 }
	 /**************************************************************************************************************************/
	 //DONE : 모임 상세
	 // time,title,age,userNo,profileImage,username,address,meetImage, commentNum,peopleLimit,peopleNum,lat,lon,personClosed
	 @Query(value="SELECT m.meetNo AS meetNo, c.name as categoryName,"
	 		+ "m.time AS TIME, m.title as title,"
	 		+ " m.age as age,"
	 		+ " m.userNo as userNo, u.profileImage as userProfileImage,"
	 		+ " u.username as username, m.address AS address, mi.image AS meetimage, m.commentNum as commentNum, "
	 		+ " m.peopleLimit as peopleLimit,"
	 		+ "   m.peopleNum as peopleNum, m.lat as lat,m.lon as lon, m.personClosed AS personClosed,"
	 		+ "   m.fee AS fee,m.content AS content, m.approval AS approval, m.timeClosed AS timeClosed ,m.meetImage as meetImage  "
	 		+ " FROM Meet m INNER JOIN Category c ON m.categoryNo = c.categoryNo"
	 		+ " INNER JOIN User u ON m.userNo = u.userNo"
	 		+ " LEFT OUTER JOIN MeetImage mi ON m.meetNo = mi.meetNo"
	 		+ " WHERE m.meetNo = :meetNo", nativeQuery = true)
	 List<Object[]> findByDetailMeetNo(@Param("meetNo") int meetNo);
	 
	 
	 /***********************************************************************************************************************/
	 //DONE : 모임 댓글 3개 리스트
	 @Query (value =  "SELECT "
		        + "    mc.meetCommentNo, "
		        + "    mc.meetNo, "
		        + "    mc.parentNo, "
		        + "    mc.content, "
		        + "    mc.userNo, "
		        + "    mc.createDate, "
		 		+ "    CASE "
		 		+ "        WHEN mc.parentNo = -1 THEN '댓글' "
		 		+ "        ELSE '대댓글' "
		 		+ "    END AS commentType,"
		 		+ "	mc.isReported "
		 		+ "FROM "
		 		+ "    meetcomment mc "
		 		+ "LEFT JOIN "
		 		+ "    meetcomment parent ON parent.meetCommentNo = mc.parentNo "
		 		+ "WHERE "
		 		+ "    mc.meetNo =:meetNo "
		 		+ "ORDER BY "
		 		+ "    COALESCE(parent.createDate, mc.createDate) DESC, "
		 		+ "    commentType DESC,"
		 		+ "    mc.createDate ASC "
		 		+ " LIMIT 3", nativeQuery = true)
		 List<Object[]> selectCommentSomeByMeetNo(@Param("meetNo") int meetNo);
		 /*****************************************************************************************************************/
		 // 모임 댓글 전체 리스트
		 @Query(value = "SELECT "
			        + "    mc.meetCommentNo, "
			        + "    mc.meetNo, "
			        + "    mc.parentNo, "
			        + "    mc.content, "
			        + "    mc.userNo, "
			        + "    mc.createDate, "
			        + "    CASE "
			        + "        WHEN mc.parentNo = -1 THEN '댓글' "
			        + "        ELSE '대댓글' "
			        + "    END AS commentType "
			        + ", mc.isReported "
			        + "FROM "
			        + "    meetcomment mc "
			        + "LEFT JOIN "
			        + "    meetcomment parent ON parent.meetCommentNo = mc.parentNo "
			        + "WHERE "
			        + "    mc.meetNo = :meetNo "
			        + "ORDER BY "
			        + "    COALESCE(parent.createDate, mc.createDate) DESC, "
			        + "    commentType DESC, "
			        + "    mc.createDate ASC",
			        nativeQuery = true)
			List<Object[]> selectCommentByMeetNo(@Param("meetNo") int meetNo);
	/***********************************************************************************************************************/
	
			@Query (value = "SELECT m.userNo , COUNT(m.meetNo) FROM Meet m WHERE m.categoryNo = :categoryNo  GROUP BY m.userNo  ORDER BY COUNT(m.meetNo) DESC LIMIT 6;",nativeQuery = true)
			List<Object[]> popularUserCategory(int categoryNo); 
			
			@Query (value = "SELECT m.userNo , COUNT(m.meetNo) FROM Meet m  GROUP BY m.userNo  ORDER BY COUNT(m.meetNo) DESC LIMIT 6;",nativeQuery = true)
			List<Object[]> popularUserAll(); 
			
			
	 // DONE : 모임 참가자 리스트
	 @Query (value =" SELECT u.userNo, u.profileImage AS userProfileImage, "
	 		+ " u.username  FROM User u "
	 		+ " INNER JOIN MeetUser m ON m.userNo = u.userNo "
	 		+ " WHERE m.meetNo = :meetNo", nativeQuery = true )
	 List<Object[]> findListMeetUser(@Param("meetNo") int meetNo);
	 
	 
	 @Query(value="UPDATE Meet SET peopleNum = peopleNum + 1 WHERE meetNo = :meetNo", nativeQuery = true )
	 void updateUserMeetPlus(@Param("meetNo") int meetNo);
	 
	 
	 @Query(value="UPDATE Meet SET peopleNum = peopleNum - 1 WHERE meetNo = :meetNo", nativeQuery = true )
	 void updateUserMeetMinus(@Param("meetNo") int meetNo);
	 
	 
	 @Query(value="UPDATE Meet SET personClosed = false WHERE meetNo = :meetNo", nativeQuery = true )
	 void updateMeetLimitFalse(@Param("meetNo") int meetNo);
	 
	 @Query(value="UPDATE Meet SET personClosed = true WHERE meetNo = :meetNo", nativeQuery = true )
	 void updateMeetLimitTrue(@Param("meetNo") int meetNo);
	 
	 
	 @Query(value="UPDATE Meet SET commentNum = commentNum +1 WHERE meetNo = :meetNo", nativeQuery = true )
	 void updateCommentPlus(@Param("meetNo") int meetNo);

	 List<Meet> findByAddressContainingAndUser_UserNo(String address, int userNo);
}

