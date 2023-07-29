package com.cos.todaymeet.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cos.todaymeet.DTO.ChatParticipantList;
import com.cos.todaymeet.DTO.NoticeCreateDTO;
import com.cos.todaymeet.DTO.NoticeDetailDto;
import com.cos.todaymeet.DTO.NoticeListDto;
import com.cos.todaymeet.DTO.NotificationListDTO;
import com.cos.todaymeet.DTO.PopularCategoryDto;
import com.cos.todaymeet.DTO.ReportProcessingDto;
import com.cos.todaymeet.DTO.ReqReportsDto;
import com.cos.todaymeet.DTO.UserCategoryDto;
import com.cos.todaymeet.DTO.UserDTO;
import com.cos.todaymeet.Service.FirebaseCloudMessageService;
import com.cos.todaymeet.Service.NoticeService;
import com.cos.todaymeet.model.Block;
import com.cos.todaymeet.model.Category;
import com.cos.todaymeet.model.FAQ;
import com.cos.todaymeet.model.Follow;
import com.cos.todaymeet.model.Inquiry;
import com.cos.todaymeet.model.Meet;
import com.cos.todaymeet.model.MeetComment;
import com.cos.todaymeet.model.MeetUser;
import com.cos.todaymeet.model.Notice;
import com.cos.todaymeet.model.NoticeVo;
import com.cos.todaymeet.model.Notification;
import com.cos.todaymeet.model.Reports;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.BlockRepository;
import com.cos.todaymeet.repository.CategoryRepository;
import com.cos.todaymeet.repository.FQARepository;
import com.cos.todaymeet.repository.FollowRepository;
import com.cos.todaymeet.repository.InquiryRepository;
import com.cos.todaymeet.repository.MeetCommentRepository;
import com.cos.todaymeet.repository.MeetRepository;
import com.cos.todaymeet.repository.MeetUserRepository;
import com.cos.todaymeet.repository.NoticePhotoRepository;
import com.cos.todaymeet.repository.NoticeRepository;
import com.cos.todaymeet.repository.NotificationRepository;
import com.cos.todaymeet.repository.ReportsRepository;
import com.cos.todaymeet.repository.UserRepository;

@RestController
public class OtherController {
	@Value("${url}")
	private String allurl;
	@Autowired 
	private MeetUserRepository meetUserRepository;
	@Autowired 
	private MeetRepository meetRepository;
	@Autowired 
	private FollowRepository followRepository;
	@Autowired 
	private UserRepository useRepository;
	@Autowired 
	private NotificationRepository notificationRepository;
	@Autowired 
	private InquiryRepository inquiryRepository;
	@Autowired
	private NoticeService noticeService;
	@Autowired 
	private NoticeRepository noticeRepository;
	@Autowired 
	private NoticePhotoRepository noticephotoRepository;
	@Autowired
	private FQARepository fqarepository;
	@Autowired 
	private CategoryRepository categoryRepository;
	@Autowired
	private ReportsRepository reportRepository;
	@Autowired 
	private MeetCommentRepository meetCommentRepository;
	@Autowired
	private BlockRepository blockRepository;

	/**************************************************************************************/
	//API 관심 카테고리 건수 개수 알림 서비스
	//TODO 구현 아직 못함..
	@GetMapping("/interest-category")
	public List<PopularCategoryDto> interestC(){
		List<Object[]> popularlist = meetUserRepository.popularCategory();
		List<PopularCategoryDto> category = new ArrayList<>();
		for (Object[] p : popularlist) {
			PopularCategoryDto c = new PopularCategoryDto((String)p[0],(Long)p[1]);
			category.add(c);
		}
		return category;
	}
	

	/****************************************************************************/
	//API 인기카테고리
	//postman others/실시간 인기 카테고리
	//4일 동안 가장 많은 참가자가 참가한 카테고리를 출력한다.
	//만약 참가자가 없는 경우는 출력되지 않는 문제가 있다.
	@GetMapping("/popular-category")
	public List<PopularCategoryDto> popularC(){
		List<Object[]> popularlist = meetUserRepository.popularCategory();
		List<PopularCategoryDto> category = new ArrayList<>();
		for (Object[] p : popularlist) {
			PopularCategoryDto c = new PopularCategoryDto((String)p[0],(Long)p[1]);
			category.add(c);
		}
		return category;
	}
	/************************* 카테고리별 가장 많은 건수를 만든 사용자 5명 *****************************************************/
	//API 카테고리 - 추천 사용자
	//postman firebase알림/탐색페이지- 추천호스트 -카테고리별
	//카테고리별로 가장 많은 모임을 주최한 사용자를 5명 출력한다.
	//본인을 제외한다.
	@PostMapping("/popular-user")
	public List<ChatParticipantList> popularUser(@RequestBody UserCategoryDto categoryUser){
		List<ChatParticipantList> partiList = new ArrayList<>();
		//카테고리 no를 가져온다.
		Category category = categoryRepository.findByName(categoryUser.getCategoryname());
		List<Object[]> userList = new ArrayList<>();
		if(category==null || categoryUser.getCategoryname()=="전체보기"){
			//잘못된 선택 or 전체보기
			userList = meetRepository.popularUserAll();
		}else {
			//특정 카테고리 검색
			userList = meetRepository.popularUserCategory(category.getCategoryNo());
		}//userNo와 count의 리스트가 출력된다.
		Integer limit =0; // 5개를 가져오기 위한 변수
		for(Object[] user : userList) {
			if(limit == 5) {
				//만약 5명을 다 가져왔다면
				break;
			}
			//System.out.println(user);
			int userNo = (int) user[0];
			if(userNo == categoryUser.getUserNo()) {
				//만약 본인이면 출력하지 않는다.
				continue;
			}
			User popularUser = useRepository.findByUserNo(userNo);
			//현재 유저가 해당 유저를 팔로우하고 있는지 판단한다.
			Integer count = followRepository.isFollow(categoryUser.getUserNo(), popularUser.getUserNo());
			System.out.println(count);
			boolean isFollow = count > 0;
			ChatParticipantList u = new ChatParticipantList(popularUser.getUsername(),popularUser.getProfileImage(),popularUser.getUserNo(),isFollow);
			//username, profileImage, userNo, follow
			partiList.add(u);
			limit = limit+1;
		}
		return partiList;
	}
	/**
	 * @throws IOException ***************************************************************************/
	//API 팔로우
	//postman others/follow-unfollow
	//특정 사용자를 팔로우한다.
	@PostMapping("/follow/add")
	public ResponseEntity<String> follow(@RequestBody Follow follow ) throws IOException{
		Integer followerNo = follow.getFollowerNo();
	    Integer followeeNo = follow.getFolloweeNo();
	    //이미 팔로우한 상태인지 확인
	    Integer isFollow = followRepository.isFollow(followerNo, followeeNo);
	    //System.out.println(isFollow);
	    if(isFollow>0) {
	    	//이미 팔로우한 유저라면 already follow 출력
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already follow");
	    }
	    // 팔로우 한 대상에게 현재 유저가 팔로우 하였다고 fcm알림을 보낸다/
	    String token = useRepository.selectToken(follow.getFolloweeNo());
	    User followerData = useRepository.findByUserNo(follow.getFollowerNo());
	    String followername = followerData.getUsername();
	    //User의 follower ,followee 개수를 +1 한다.
	    useRepository.updateFollowNumPlus(follow.getFollowerNo());
		useRepository.updateFolloweeNumPlus(follow.getFolloweeNo());
		//알림 전송
		fcmService.sendMessageTo(token, "follow", followername+"님이 나를 팔로우 합니다",
				followername,String.valueOf(followerData.getUserNo()),null,"1",followerData.getProfileImage());
		//팔로우 받는 사람에게 팔로우 알림 저장.
		Notification notice = new Notification(useRepository.findByUserNo(followeeNo),1,followerNo,null,followername,followerData.getProfileImage());
		notificationRepository.save(notice);
		followRepository.save(follow);	
		return ResponseEntity.ok("add follow success!!!");
	}
	/**
	 * @throws IOException ***************************************************************************/
	//API 언팔로우
	//postman others/follow-unfollow
	//TODO 팔로우 한 상태인지 판단하는 것이 없다.
	@PostMapping("/follow/minus")
	public ResponseEntity<String> unfollow(@RequestBody Follow follow ){
		Integer followerNo = follow.getFollowerNo();
	    Integer followeeNo = follow.getFolloweeNo();
	    //followee, follower 수를 -1 한다
		useRepository.updateFollowNumMinus(follow.getFollowerNo());
		useRepository.updateFolloweeNumMinus(follow.getFolloweeNo());
		//follow테이블에서 값을 삭제
		followRepository.deleteByFollowerNoAndFolloweeNo(followerNo, followeeNo);
		
		return ResponseEntity.ok("un follow success!!!");
	}
	/*********************************************************************************/
	@Autowired
	private FirebaseCloudMessageService fcmService;
	//TEST 알림 생성 - 테스트용
	//알림을 db에 저장하는 역할을 한다, 테스트용이다. 실제 사용하지 않는다
	@PostMapping("/notifi/make")
	public ResponseEntity<String> notifimake(@RequestBody Notification noti ) throws IOException{
		noti.setConfirmed(false);
		User user = useRepository.findByUserNo(noti.getUser().getUserNo());
		noti.setUser(user);
		notificationRepository.save(noti);
		String token =user.getToken();
		//fcmService.sendMessageTo(token, noti.getNotiType(.toString()), noti.getNumber().toString() , noti.getName(),noti.getNumber().toString(), noti.getNotiType().toString(),noti.getImageLink());
		return null;
	}
	/*****************************************************************************/
	//API 알림 출력
	//postman others/사용자알림리스트출력
	//사용자의 알림 리스트를 출력한다.
	@GetMapping("/notifi/all")
	public List<NotificationListDTO> notifiList(@RequestParam int userNo){
		
		List<Object[]> notifi = notificationRepository.selectNotifi(userNo);
		List<NotificationListDTO> notilist = new ArrayList<>();
		for(Object[] noti:notifi) {
			//현지 시간으로부터 몇 초(분,시간) 전에 저장된 알림인지
			Timestamp times = (Timestamp)noti[0];
			String time = getNotificationTimeAgo( times.toLocalDateTime());
			//time('n초 전'과 같은 형태로 출력), userNumber(userNo - 팔로우 한 사람과 같은,,,), meetNumber(meetNo ), name, imageLink, notiType(알림의 타입), processed(참가 요청 처리(수락/반려)를 하였는지 저장)
			NotificationListDTO n = new NotificationListDTO(time,(Integer) noti[1],(Integer) noti[2],noti[3].toString(),noti[4].toString(),(Integer) noti[5],(boolean) noti[6]);
			notilist.add(n);
		}
		
		return notilist;
	}
	/**************************************************************************/
	//API 문의 등록
	//postman others/문의등록
	//문의를 저장한다.
	@PostMapping("/inquiry")
	public ResponseEntity<String> inquirymake(@RequestBody Inquiry inquiry ){
		User user = useRepository.findByUserNo(inquiry.getUser().getUserNo());
		inquiry.setUser(user);
		inquiryRepository.save(inquiry);
		
		return null;
	}
	
	/****************************************************************************/
	//등록 시간 계산 함수
	public static String getNotificationTimeAgo(LocalDateTime creationTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(creationTime, currentTime);
        
        long years = duration.toDays() / 365;
        long months = duration.toDays() / 30;
        long weeks = duration.toDays() / 7;
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();
        
        if (years > 0) {
            return years + "년 전";
        } else if (months > 0) {
            return months + "달 전";
        } else if (weeks > 0) {
            return weeks + "주 전";
        } else if (days > 0) {
            return days + "일 전";
        } else if (hours > 0) {
            return hours + "시간 전";
        } else if (minutes > 0) {
            return minutes + "분 전";
        } else {
            return seconds + "초 전";
        }
    }
	/*********************************************************************************/
	//API 공지사항 등록
	//postman ohters/공지사항 등록
	//공지사항을 데이터베이스에 저장한다. 이미지도 포함할 수 있다.
	@PostMapping("/notice")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody int notice(NoticeVo noticeVO)  throws Exception{
		NoticeCreateDTO requestDTO =
				NoticeCreateDTO.builder()
				.content(noticeVO.getContent())
				.title(noticeVO.getTitle())
				.isDeleted(noticeVO.isDeleted())
				.build();
				
		Notice notice = new Notice();
		return noticeService.create(requestDTO, noticeVO.getFiles());
		
	}
	/**********************************************************************************************/
	//API 공지사항 리스트 출력
	//imagelink가 null이면 오류 발생하는 문제가 발생 
	@GetMapping("/notice/list")
	public @ResponseBody List<NoticeListDto> noticeList() {
		List<Object[]> objectlist = noticeRepository.findNoticeListAll();
		List<NoticeListDto> noticeList = new ArrayList<>();
		for (Object[] object : objectlist) {
			NoticeListDto notice = new NoticeListDto((int)object[3],object[0].toString(),object[1].toString(),(Timestamp) object[2]);
			noticeList.add(notice);
		}
		return noticeList;
	}
	/*******************************************************************************************/
	//API 공지사항 상세 출력
	//공지사항 상세화면 출력
	@GetMapping("/notice/detail")
	public NoticeDetailDto noticeDetail(@RequestParam int noticeNo){
		List<Object[]> notice = noticeRepository.findNoticeListByNoticeNo(noticeNo);
		Object[] noti = notice.get(0);
		//System.out.println(noti);
		List<String> imagelist = noticephotoRepository.findNoticeFileList(noticeNo);
		List<String> imageurllist = new ArrayList<>();
		for (String imageId : imagelist) {
			String url = allurl+"notice/image/"+imageId;
			//System.out.println(url);
			imageurllist.add(url);
		}
		NoticeDetailDto noticeDetail = new NoticeDetailDto(noticeNo,(String) noti[0], (String)noti[1], (Timestamp)noti[2],imageurllist);
		return noticeDetail;
	}
	/*********************************************************************************************/
	//API FAQ 등록
	@PostMapping("/FAQ")
	public int FAQMake(@RequestBody FAQ faq) throws Exception {
		//System.out.println(faq.getAnswer());
		//System.out.println(faq.getQuestion());
		//System.out.println(faq.getAnswer());
		fqarepository.save(faq);
		return 0;
	}
	/**********************************************************************************************/
	//API FAQ 출력
	@GetMapping("/FAQ/list")
	public List<FAQ> FAQlist() {
		List<FAQ> list = fqarepository.selectFAQList();
//		for (Object[] o : list) {
//			FAQ qa = new FAQ();
//		}
		return list;
	}
	/*********************************************************************************************/
	//API   나를 follow 하는 사람 리스트 출력
	//postman - others/follow리스트
	@GetMapping("/follow/list")
	public List<UserDTO> followList(@RequestParam int userNo){
		List<Integer > followNolist = followRepository.selectFolloweeNo(userNo);
		System.out.println(followNolist);
		List<UserDTO> followUserList = new ArrayList<>();
		for (Integer followerNo : followNolist) {
			List<Object[]> u = useRepository.tttt(followerNo);
			Object[] user = u.get(0);
			UserDTO userProfile = new UserDTO((int)user[0],(String )user[1],(String )user[2]);
			followUserList.add(userProfile);
		}
		return followUserList;
	}
	/*********************************************************************************************/
	//API  followee
	//postman - others/follow리스트
	@GetMapping("/followee/list")
	public List<UserDTO> followeeList(@RequestParam int userNo){
		List<Integer > followNolist = followRepository.selectFollowerNo(userNo);
		System.out.println(followNolist);
		List<UserDTO> followUserList = new ArrayList<>();
		for (Integer followerNo : followNolist) {
			List<Object[]> u = useRepository.tttt(followerNo);
			Object[] user = u.get(0);
			UserDTO userProfile = new UserDTO((int)user[0],(String )user[1],(String )user[2]);
			followUserList.add(userProfile);
		}
		return followUserList;
	}
	
	
	/**
	 * @throws IOException ******************************************************************************************/
	//API 승인 요청 
	//postman firebase알림/참가신청알림포함
	//참가 신청을 하면 모임의 host에게 알림이 저장되고, 기기로 알림이 간다.
	@PostMapping("/approval/make")
	public int Prov(@RequestBody MeetUser meetuser) throws IOException {
		//알림 등록
		Meet meet = meetRepository.findByMeetNo(meetuser.getMeet().getMeetNo());
		User hostuser =meet.getUser();
		User user = useRepository.findByUserNo(meetuser.getUser().getUserNo());
		Notification notifi = new Notification();
		notifi.setMeetNumber(meetuser.getMeet().getMeetNo());
		notifi.setName(user.getUsername());
		notifi.setUserNumber(meetuser.getUser().getUserNo());
		notifi.setNotiType(7);
		notifi.setImageLink(user.getProfileImage());
		notifi.setUser(hostuser);
		notifi.setConfirmed(false);
		notificationRepository.save(notifi);
		
		//hostuser에게 알림 보내기
		
		fcmService.sendMessageTo(hostuser.getToken(), "참가 승인 요청", user.getUsername()+"님이 참가요청을 했습니다.",user.getUsername()
				,String.valueOf(user.getUserNo()),String.valueOf(meetuser.getMeet().getMeetNo()),"7",user.getProfileImage());
		
		
		return 0;
	}
	
	/****************************************************************************************************************/
	//API 승인
	//postman - firebase알림/참가신청 알림포함
	@PostMapping("/approval/ok")
	public int approvalOK(@RequestBody MeetUser meetUser) throws IOException {
		User user = useRepository.findByUserNo(meetUser.getUser().getUserNo());
		    try {
		    	//받은 참가자를 모임에 추가한다.
		        int hostNo = meetRepository.findUserNoByMeetNo((int)meetUser.getMeet().getMeetNo());
		        
		        //System.out.println(meetUser.getUser().getUserNo());
		        //System.out.println(hostNo);
		        if (meetUser.getUser().getUserNo() == hostNo) {
		        	System.out.println("Host!!");
		            meetUser.setHost(true);
		        }
		        MeetUser meetuser = meetUserRepository.findByUserNoAndMeetNo1(meetUser.getUser().getUserNo(), meetUser.getMeet().getMeetNo());
		        int meetNo = meetUser.getMeet().getMeetNo();
		        System.out.println(meetNo);
		        Meet meet = meetRepository.findByMeetNo(meetNo);
		        System.out.println(meetNo);
		        boolean closed = meet.isPersonClosed();
		        System.out.println(closed);
		        if(closed) {
		        	//return ResponseEntity.ok("closed!!!");
		        }else {
		        	if(meetuser==null) {
			        	System.out.println(meetNo);
			        	meetUserRepository.save(meetUser);
			        	System.out.println(meetNo);
				        meetRepository.updateUserMeetPlus(meetUser.getMeet().getMeetNo());
			        	System.out.println("meetuseradd success!!");
				        System.out.println(meetUser);
				        if((meet.getPeopleNum()+1 )== meet.getPeopleLimit()) {
				        	meetRepository.updateMeetLimitTrue(meetNo);
				        //return ResponseEntity.ok("add meet user success!!!");
				        	}
				        }
			        else {
			        	//eturn ResponseEntity.ok("already added!!!");
			        	}
		        	}
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("add meet user error!!");
		    }
		    
		    Meet meet = meetRepository.findByMeetNo(meetUser.getMeet().getMeetNo());
		    String imageLink = meet.getMeetImage();
			if(meet.getMeetImage()==null) {
				imageLink = allurl+"meetimage/0";
			}
		    //참가를 신청한 사용자에게 승인 알림을 저장하고
		    Notification notifi = new Notification();
			notifi.setMeetNumber(meet.getMeetNo());
			notifi.setName(meet.getTitle());
			notifi.setUserNumber(user.getUserNo());
			notifi.setNotiType(4);
			notifi.setImageLink(imageLink);
			notifi.setUser(user);
			notifi.setConfirmed(false);
			notificationRepository.save(notifi);
			
			//참가를 신청한 사용자에게 승인 알림을 전송한다.
		    fcmService.sendMessageTo(user.getToken(), "참가 승인", meet.getTitle()+"참가가 승인되었습니다.", meet.getTitle()
					,String.valueOf(user.getUserNo()),String.valueOf(meet.getMeetNo()),"4",imageLink);
		    //해당 참가신청 알림을 처리됨으로 저장한다.
		    notificationRepository.isProcessed(meetUser.getUser().getUserNo(),meetUser.getMeet().getMeetNo());
		
		return 0;
		
	}
	//API 승인 거절
	//postman -firebase알림/참가신청알림포함
		@PostMapping("/approval/not")
		public int approvalNot(@RequestBody MeetUser meetUser) throws IOException{
			 Meet meet = meetRepository.findByMeetNo(meetUser.getMeet().getMeetNo());
			 User user = useRepository.findByUserNo(meetUser.getUser().getUserNo());
			 String imageLink = meet.getMeetImage();
				if(meet.getMeetImage()==null) {
					imageLink = allurl+"meetimage/0";
				}
			 //참가를 신청한 사용자에게 거절 알림을 저장하고
			 Notification notifi = new Notification();
				notifi.setMeetNumber(meet.getMeetNo());
				notifi.setName(meet.getTitle());
				notifi.setUserNumber(user.getUserNo());
				notifi.setNotiType(5);
				notifi.setImageLink(imageLink);
				notifi.setUser(meetUser.getUser());
				notifi.setConfirmed(false);
				notificationRepository.save(notifi);
				//참가를 신청한 사용자에게 거절 알림을 전송한다,
			fcmService.sendMessageTo(user.getToken(), "참가 거절", meet.getTitle()+"참가가 거절되었습니다.",meet.getTitle()
					,String.valueOf(user.getUserNo()),String.valueOf(meet.getMeetNo()),"5",imageLink);
			//해당 참가신청 알림을 처리됨으로 저장한다.
			notificationRepository.isProcessed(meetUser.getUser().getUserNo(),meetUser.getMeet().getMeetNo());
			return 0;
			
		}
		
	
		/***********************************************************************************************/
		//API 신고 등록
		//postman - others/신고 등록/처리
		//userNo (신고 하는 사용자) meetCommentNo (신고된 댓글) , type(유형)
		//신고를 저장한다.
		@PostMapping("/reports")
		public int addReports(@RequestBody ReqReportsDto req) {
			MeetComment meetcomment = meetCommentRepository.findByMeetCommentNo(req.getMeetcommentNo());
			User user = useRepository.findByUserNo(req.getUserNo());
			Reports report = new Reports().builder()
					.user(user)
					.meetcomment(meetcomment)
					.type(req.getType())
					.status(1)
					.build();
			
		reportRepository.save(report);
			
			return 0;
		}
		/*********************************************************************************************/
		//API 신고 처리
		//postman - others/신고 등록/처리
		//관리자가 신고를 처리한다.
		//reportNo(신고번호), comment, status(-1 반려 1 처리중 2 승인)
		@PostMapping("reports/process")
		public void processReports(@RequestBody ReportProcessingDto req) {
			
			Reports report = reportRepository.findByReportNo(req.getReportNo());
			report.setComment(req.getComment());
			report.setStatus(req.getStatus());
			MeetComment meetComment = meetCommentRepository.findByMeetCommentNo(report.getMeetcomment().getMeetCommentNo());
			//댓글 테이블에 신고 승인 여부를 저장해서 출력할 때 사용하도록 한다.
			if(req.getStatus()==2) {
				//신고 승인
				meetComment.setIsReported(true);
			}else {
				//신고 반려, 해제
				meetComment.setIsReported(false);
			}
			//처리시간 저장
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			report.setProcessTime(currentTimestamp);
			meetCommentRepository.save(meetComment);
			reportRepository.save(report);
		}
		/*********************************************************************************************/
		//API 차단 추가
		//postman - others/차단 생성/해제
		@PostMapping("/block/add")
		public void addblock(@RequestBody Block block) {
			blockRepository.save(block);
		}
		//API 차단 해제
		//postman - others/차단 생성/해제
		@PostMapping("/block/not")
		public void unblock(@RequestBody Block block) {
			blockRepository.deleteByBlockingUserNoAndBlockedUserNo(block.getBlockingUserNo(),block.getBlockedUserNo());
		}
		//API 차단한 사용자 리스트
		//postman - others/차단리스트
		//사용자가 차단한 사용자의 리스트를 출력한다.
		@GetMapping("/block/list/{userNo}")
		public List<UserDTO> blockList(@PathVariable("userNo") int userNo) {
			List<Integer> blockedUserNoList = blockRepository.selectBlockedNo(userNo);
			List<UserDTO> userDtoList = new ArrayList<>();
			for(Integer blockedUserNo : blockedUserNoList) {
				User user = useRepository.findByUserNo(blockedUserNo);
				UserDTO userDto = new UserDTO(blockedUserNo, user.getProfileImage(),user.getUsername());
				userDtoList.add(userDto);
			}
			return userDtoList;
		}
		//API 광고 1개
		@GetMapping("ad/one")
		public String adone() {
			Random random = new Random();
			int randomNum = random.nextInt(4) + 1;
			
			return "http://todaymeet.shop:8080/ad/"+String.valueOf(randomNum);	
		}
		//API 광고 3개
		@GetMapping("ad/three")
		public List<String> adthree() {
			Random random = new Random();
			List<String> urllist = new ArrayList<>();
			urllist.add("http://todaymeet.shop:8080/ad/1");		
			urllist.add("http://todaymeet.shop:8080/ad/2");	
			urllist.add("http://todaymeet.shop:8080/ad/3");	
			return urllist;	
		}
}
