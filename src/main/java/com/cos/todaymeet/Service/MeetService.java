package com.cos.todaymeet.Service;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.todaymeet.DTO.MeetCommentDTO;
import com.cos.todaymeet.DTO.MeetCreateRequestDTO;
import com.cos.todaymeet.DTO.MeetDetailDTO;
import com.cos.todaymeet.DTO.UserDTO;
import com.cos.todaymeet.model.Meet;
import com.cos.todaymeet.model.MeetPhoto;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.BlockRepository;
import com.cos.todaymeet.repository.MeetFileHandler;
import com.cos.todaymeet.repository.MeetPhotoRepository;
import com.cos.todaymeet.repository.MeetRepository;
import com.cos.todaymeet.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MeetService {
	@Autowired 
	private MeetRepository meetRepository;
	@Autowired
	private BlockRepository blockRepository;
//	public User getUserOne(String userNo) {
//		return userRepository.findByUserNo(int userNo);
//				//.orElseThrow(()->new TestException(HttpStatus.NOT_FOUND,"없는 USERNO"));
//		
//	}
//	public void UpdatePhoneNumber(String userNo, String phoneNumber) {
//		User user = getUserOne(userNo);
//		user.setPhoneNumber(phoneNumber);
//		userRepository.save(user);
//	}

	@Autowired
    private UserRepository userrepository;
	private final MeetPhotoRepository photoRepository;
	private final MeetPhotoRepository meetphotoRepository;
    private final MeetFileHandler fileHandler;
    
	@Value("${url}")
	private String allurl;
    @Transactional
    public void update(
    	int id,
    	MeetCreateRequestDTO requestDto,
        List<MultipartFile> files
        ,String thumbnail
    ) throws Exception {
        Meet board = meetRepository.findByMeetNo(id);
          

        List<MeetPhoto> photoList = fileHandler.parseFileInfo(board, files);
        System.out.println(id);
        Long meetId = Long.valueOf(id);
        System.out.println(meetId);
        if(!photoList.isEmpty()){
        	board.setMeetImage(null);
            for(MeetPhoto photo : photoList) {
            	//photo.setMeet(board);
            	System.out.println(photo);
            	MeetPhoto mp = meetphotoRepository.save(photo);
            	if(board.getMeetImage()==null) {
            		System.out.println("meet image "+board.getMeetImage());
            		board.setMeetImage(allurl+"meetimage/"+mp.getId());
            		System.out.println("meet image "+board.getMeetImage());
            	}
            	board.addPhoto(mp);
            }
        }System.out.println(id);
        String thumbnailImage = null;
        if(thumbnail!="") {
        	thumbnailImage = allurl+"meetimage/"+meetphotoRepository.findmeetImage(id,thumbnail);
        	System.out.println(thumbnailImage);
        }
        
        Meet meet = new Meet(
        		requestDto.getCategory(),
        		requestDto.getAddress(),
        		requestDto.getLat(),
        		requestDto.getLon(),
        		requestDto.getTime(),
        		requestDto.isAge(),
        		requestDto.getPeopleLimit(),
        		requestDto.getFee(),
        		requestDto.getTitle(),
        		requestDto.getContent(),
        		requestDto.isApproval(),
        		board.getPeopleNum(),
        		board.isTimeClosed(),
        		board.isPersonClosed(),
        		board.getCommentNum(),
        		requestDto.getUser(),
        		board.getCreateTime(),
        		thumbnailImage); 
        board.update(meet);
        System.out.println(meet.getMeetNo());
    }
    
    //DONE : 모임생성 => create
    @Transactional
    public int create(
    	MeetCreateRequestDTO requestDto,
        List<MultipartFile> files
    ) throws Exception {
	// 파일 처리를 위한 meet 객체 생성
        Meet meet = new Meet(
        		requestDto.getCategory(),
        		requestDto.getAddress(),
        		requestDto.getLat(),
        		requestDto.getLon()
        		,requestDto.getTime(),
        		requestDto.isAge(),
        		requestDto.getPeopleLimit(),
        		requestDto.getFee(),
        		requestDto.getTitle(),
        		requestDto.getContent(),
        		requestDto.isApproval(),
        		requestDto.getPeopleNum(),
        		requestDto.isTimeClosed(),
        		requestDto.isPersonClosed(),
        		requestDto.getCommentNum(),
        		requestDto.getUser(),
        		requestDto.getTime(),
        		null // createTime으로 변경해야함
        		
        );

        List<MeetPhoto> photoList = fileHandler.parseFileInfo(meet, files);

  	 // 파일이 존재할 때에만 처리
        if(!photoList.isEmpty()) {
        	MeetPhoto p = photoList.get(0);
        	//System.out.println(p);
            for(MeetPhoto photo : photoList) {
                // 파일을 DB에 저장
            	MeetPhoto mp = meetphotoRepository.save(photo);
            	if(meet.getMeetImage()==null) {
            		//thumbnail 처리 
            		//TODO : thumbnail url을 전역변수로 선언하여 지정하자
            		meet.setMeetImage(allurl+"meetimage/"+mp.getId());
            	}
  		        meet.addPhoto(mp);
            }
        }
        return meetRepository.save(meet).getMeetNo();
    }
	
    public void updateMeet(int meetNo, String title,String content,Integer peopleLimit) {
        Meet user = meetRepository.findByMeetNo(meetNo);//.orElse(null);
        if (user != null) {
        	user.setTitle(title);
        	user.setContent(content);
        	user.setPeopleLimit(peopleLimit);
        }
    }
    /**********************************************************************************************************************************/
    // 댓글 정보에 작성자 정보를 추가해서 return 
	public List<MeetCommentDTO> convertToMeetComment(List<Object[]> objectList) {
		//저장할 배열
	    List<MeetCommentDTO> meetList = new ArrayList<>();
	    // 받은 댓글을 하나씩 확인
	    for (Object[] objects : objectList) {
	    	//받은 댓글 정보를 저장
	        int meetCommentNo = (int)objects[0];
	    	int meetNo = (int) objects[1];
	    	//System.out.println(meetNo);
	    	//Meet meet = (Meet) meetRepository.findByMeetNo(meetNo);
	    	//System.out.println(meet.toString());
	        Integer parentNo = (Integer) objects[2];
	        String content = (String) objects[3];
	        int userNo = (int) objects[4];
	        Timestamp createdate = (Timestamp)objects[5];
	        //System.out.println(userNo);
	        //작성자 정보를 가져옴
	        List<Object[]> user1 = userrepository.tttt(userNo);
	        Object[] user = user1.get(0);
	        //System.out.println("userNo"+userNo + ":"+user);
//	        UserDTO userdto = new UserDTO((int)user.getUserNo(),(String)user.getProfileImage(),
//	        		(String)user.getUsername());
	        //System.out.println(user.toString());
	        //User userDTO = new User((int)user[0],(String)user[1],(String)user[2]);
	        //받은 정보를 저장
	        boolean isReported = (boolean)objects[7];
	        if(isReported==true ) {
	        	content = "신고된 댓글입니다.";
	        }
	        MeetCommentDTO meetDTO = new MeetCommentDTO(meetCommentNo,meetNo,
	        		parentNo,createdate,content,userNo,
	        		(String)user[1],(String)user[2]);
	        //System.out.println(meetDTO.toString());
	        meetList.add(meetDTO);
	    }
	    
	    return meetList;
	}
	/**********************************************************************************************************************************/
    // 댓글 정보에 작성자 정보를 추가해서 return 
	public List<MeetCommentDTO> convertToMeetCommentBlock(List<Object[]> objectList,int blockingUserNo) {
		//저장할 배열
	    List<MeetCommentDTO> meetList = new ArrayList<>();
	    // 받은 댓글을 하나씩 확인
	    for (Object[] objects : objectList) {
	    	//받은 댓글 정보를 저장
	        int meetCommentNo = (int)objects[0];
	    	int meetNo = (int) objects[1];
	    	//System.out.println(meetNo);
	    	//Meet meet = (Meet) meetRepository.findByMeetNo(meetNo);
	    	//System.out.println(meet.toString());
	        Integer parentNo = (Integer) objects[2];
	        String content = (String) objects[3];
	        int userNo = (int) objects[4];
	        Timestamp createdate = (Timestamp)objects[5];
	        //System.out.println(userNo);
	        //작성자 정보를 가져옴
	        List<Object[]> user1 = userrepository.tttt(userNo);
	        Object[] user = user1.get(0);
	        //System.out.println("userNo"+userNo + ":"+user);
//	        UserDTO userdto = new UserDTO((int)user.getUserNo(),(String)user.getProfileImage(),
//	        		(String)user.getUsername());
	        //System.out.println(user.toString());
	        //User userDTO = new User((int)user[0],(String)user[1],(String)user[2]);
	        //받은 정보를 저장
	        boolean isReported = (boolean)objects[7];
	        if(isReported==true ) {
	        	content = "신고된 댓글입니다.";
	        }
	        Integer isBlocked = blockRepository.isBlock(blockingUserNo, userNo);
	        if(isBlocked >0) {
	        	content = "차단된 사용자입니다.";
	        }
	        MeetCommentDTO meetDTO = new MeetCommentDTO(meetCommentNo,meetNo,
	        		parentNo,createdate,content,userNo,
	        		(String)user[1],(String)user[2]);
	        //System.out.println(meetDTO.toString());
	        meetList.add(meetDTO);
	    }
	    
	    return meetList;
	}
	/***********************************************************************************************************************************/
	// 받은 데이터들을 meetDetailDTO라는 형식으로 모아서 return
	//meetdetail, commentlist, userlist , isInsert ,meetImagelist 
	public MeetDetailDTO convertToMeetDetailDTO(List<Object[]> meetDetails,
			List<Object[]> commentList,List<Object[]> userList,boolean isInsert,List<String> meetImagelist,int uNo){
		// 댓글에 작성자 정보를 더해서 MeetCommentDTO 형태 리스트로 return 
		List<MeetCommentDTO> comments = convertToMeetCommentBlock(commentList,uNo);
		// 참가자 리스트를 UserDTO 형태로 리턴
	    List<UserDTO> users = new ArrayList<>();
		for (Object[] objects : userList) {
			int userNo = (int)objects[0];
			String profileImage = (String)objects[1];
			String username = (String)objects[2];
			UserDTO userDTO = new UserDTO(userNo,profileImage,username);
			users.add(userDTO);
		}
		// select를 이용해서 list형태로 받아오기 때문에 get(0)
		Object[] meetDetail = meetDetails.get(0);
		// meetDetail 정보를 저장
	    int meetNo = (int) meetDetail[0];
	    String categoryName = (String) meetDetail[1];
	    Timestamp time = (Timestamp) meetDetail[2];
	    String title = (String) meetDetail[3];
	    String age ;
	    int userNo = (int) meetDetail[5];
	    boolean isage = (boolean) meetDetail[4];
	    Timestamp hostbirth = userrepository.selectBirth(userNo);
	    if(isage || hostbirth == null) {
	    	//만약 사용자의 생일이 없으면 그냥 전연령으로 표시
	    	age = "전연령";
	    }else {
	    	
	    	// 현재 시간
	        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

	        // 생일과 현재 시간 간의 차이 계산
	        LocalDate birthdayDate = hostbirth.toLocalDateTime().toLocalDate();
	        LocalDate currentDate = currentTime.toLocalDateTime().toLocalDate();
	        Period period = Period.between(birthdayDate, currentDate);

	        // 사용자의 나이 계산
	        int hostage = period.getYears();
	        int minage = hostage-5;
	        int maxage = hostage+5;
	        age = minage + " ~ "+maxage;
	        //System.out.println(age);
	    }
	    //System.out.println(age);
	    String userProfileImage = (String) meetDetail[6];
	    String username = (String) meetDetail[7];
	    String address = (String) meetDetail[8];
	    //모임 이미지 저장
	    List<String> meetImage = (List<String>) meetImagelist;
	    Integer commentNum = (Integer) meetDetail[10];
	    Integer peopleLimit = (Integer) meetDetail[11];
	    Integer peopleNum = (Integer) meetDetail[12];
	    String lat = (String) meetDetail[13];
	    String lon = (String) meetDetail[14];
	    boolean personClosed = (boolean) meetDetail[15]; // 변경하지 않음
	    Integer fee = (Integer) meetDetail[16];
	    String content = (String) meetDetail[17];
	    boolean approval = (boolean) meetDetail[18];
	    boolean timeClosed = (boolean) meetDetail[19];
		
	    //주최자 정보를 받아와서 UserDTO에 저장
		User host = userrepository.findByUserNo(userNo);
		UserDTO hostUser = new UserDTO((int)host.getUserNo(),(String)host.getProfileImage()
				,(String)host.getUsername());
		
		//이제까지 모은 정보를 MeetDetailDTO로 변환
		MeetDetailDTO meetDetailDTO = new MeetDetailDTO(
			    meetNo, categoryName, time, title, age, userNo,
			    userProfileImage, username, address, meetImage, commentNum,
			    peopleLimit, peopleNum, lat, lon, personClosed, fee, content,
			    approval, timeClosed, hostUser, comments, users, (boolean)isInsert
			);
		return meetDetailDTO;
		
	}

}
