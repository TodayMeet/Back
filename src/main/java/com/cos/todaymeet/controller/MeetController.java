package com.cos.todaymeet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.todaymeet.DTO.MeetCommentDTO;
import com.cos.todaymeet.DTO.MeetCreateRequestDTO;
import com.cos.todaymeet.DTO.MeetDetailDTO;
import com.cos.todaymeet.DTO.MeetImageListDTO;
import com.cos.todaymeet.DTO.MeetListDTO;
import com.cos.todaymeet.DTO.PhotoDTO;
import com.cos.todaymeet.DTO.SortCategoryDTO;
import com.cos.todaymeet.DTO.SortCategoryListDTO;
import com.cos.todaymeet.DTO.SortPageDTO;
import com.cos.todaymeet.DTO.UserNoDTO;
import com.cos.todaymeet.DTO.UserNoImage;
import com.cos.todaymeet.Service.FirebaseCloudMessageService;
import com.cos.todaymeet.Service.MeetService;
import com.cos.todaymeet.Service.MeetUserService;
import com.cos.todaymeet.Service.PhotoService;
import com.cos.todaymeet.Service.fileService;
import com.cos.todaymeet.model.Category;
import com.cos.todaymeet.model.Meet;
import com.cos.todaymeet.model.MeetComment;
import com.cos.todaymeet.model.MeetImageVO;
import com.cos.todaymeet.model.MeetPhoto;
import com.cos.todaymeet.model.MeetUser;
import com.cos.todaymeet.model.Notification;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.CategoryRepository;
import com.cos.todaymeet.repository.FollowRepository;
import com.cos.todaymeet.repository.MeetCommentRepository;
import com.cos.todaymeet.repository.MeetPhotoRepository;
import com.cos.todaymeet.repository.MeetRepository;
import com.cos.todaymeet.repository.MeetUserRepository;
import com.cos.todaymeet.repository.NotificationRepository;
import com.cos.todaymeet.repository.UserRepository;

//@Controller
@RestController
public class MeetController {
	@Autowired
	private MeetRepository meetRepository;
	@Autowired
	private MeetUserRepository MeetUserRepository;
	@Autowired
	private MeetCommentRepository meetCommentRepository;
	@Autowired
	private MeetService meetService;
	@Autowired
	private MeetUserService meetUserService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private fileService fileService;
	@Autowired
	private FirebaseCloudMessageService fcmService;
	@Autowired
	private MeetPhotoRepository meetphotoRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	@ResponseBody
	@GetMapping("/meet/{meetNo}")
	public Meet selectMeet(@PathVariable("meetNo") int meetNo) {
		Meet meet = meetRepository.findByMeetNo(meetNo);
		System.out.println(meet);
		//return "meet!";
		return meet;
	}
	/***************모임 생성*********************/
	@PostMapping("/meet/add")
	public ResponseEntity<String> join(@RequestBody Meet meet) {
		
		System.out.println(meet);
		//meetRepository.save(meet);
		try {
	        meetRepository.save(meet);
	        System.out.println(meet);
	        User userNo = meet.getUser();
	        int meetNo = meet.getMeetNo();
	        MeetUser meetuser = new MeetUser(meet, userNo, true);
	        MeetUserRepository.save(meetuser);
	        meetRepository.updateUserMeetPlus(meetNo);
	        return ResponseEntity.ok("add meet success!!!");
		} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("add meet error!!");
	    }
	}
	/***************모임조회 이미지***************/
    /**
     * 썸네일용 이미지 조회
     */
    @CrossOrigin
    @GetMapping(
            value = "/thumbnail/{id}",
            // 출력하고자 하는 데이터 포맷 정의
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) throws IOException {
    
    	// 이미지가 저장된 절대 경로 추출
        String absolutePath =
        	new File("").getAbsolutePath() + File.separator + File.separator;
        String path;

	
        if(id != 0) {  // 전달되어 온 이미지가 기본 썸네일이 아닐 경우
            PhotoDTO photoDto = photoService.findByFileId(id);
            path = photoDto.getFilePath();
        }
        else {  // 전달되어 온 이미지가 기본 썸네일일 경우
            path = "images" + File.separator + "thumbnail" + File.separator + "thumbnail.png";
        }
        
	// FileInputstream의 객체를 생성하여
    	// 이미지가 저장된 경로를 byte[] 형태의 값으로 encoding
        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    /**
     * 이미지 개별 조회
     */
    @CrossOrigin
    @GetMapping(
            value = "/image/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        PhotoDTO photoDto = photoService.findByFileId(id);
        String absolutePath
        	= new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
    /*************************************************************************************************************************************/
    @PostMapping("/meet/delete/{meetNo}")
    public int delete(@PathVariable int meetNo,String filePath) {
    	fileService.delete(filePath);
    	return 0;
    }
    /**********************************************************************************************************************/
    //API 모임변경 - 이미지 포함
    // 문제가 발생 - 사용자는 이미지를 url을 이용하여 접근하는데 이 기능을 사용하기 위해서는 사용자가 기존 이미지를 다운받거나 가지고 있어야한다..?
    //postman에서는 가지고 있는 상황을 쉽게 시뮬레이션할 수 있지만 프론트에서 어떻게 작용해야하는지 알 수 없기 때문에 api만 만들고 연결하지 않았다.
    // postman에서는 잘 작동하는 것을 확인할 수 있다.
    @PutMapping("/meet/update/{meetNo}")
    public int update(@PathVariable int meetNo,MeetImageVO meetImageVO) {
    	//카테고리 이름을 받아서 category 로 변환
		Category meetCategory = categoryRepository.findByName(meetImageVO.getCategoryname());
		//사용자를 받아서 변환
		System.out.println(meetCategory);
		System.out.println(meetImageVO.getCategoryname());
		User hostuser = userRepository.findByUserNo(meetImageVO.getUserNo());
    	MeetCreateRequestDTO requestDTO=
				MeetCreateRequestDTO.builder()
					.address(meetImageVO.getAddress())
					.approval(meetImageVO.isApproval())
					.age(meetImageVO.isAge())
					.category(meetCategory)
					.content(meetImageVO.getContent())
					.fee(meetImageVO.getFee())
					.lat(meetImageVO.getLat())
					.lon(meetImageVO.getLon())
					.peopleLimit(meetImageVO.getPeopleLimit())
					.time(meetImageVO.getTime())
					.title(meetImageVO.getTitle())
					.user(hostuser)
					.build();
    	//db의 저장된 것
    	List<MeetPhoto> dbPhotoList = meetphotoRepository.selectMeetNo(meetNo);
    	//받은 파일
    	List<MultipartFile> multipartList = meetImageVO.getFiles();
    	//저장할 것
    	List<MultipartFile> addFileList = new ArrayList<>();
    	//받은 파일 name list
    	List <String> multipartListName = new ArrayList<>();
    	//db에 저장된 파일의 name list
	      List<String> dbOriginNameList = meetphotoRepository.findOriginName(meetNo);
	     
	      //System.out.println(dbOriginNameList);
	      //System.out.println(multipartListName);
	      // 전달받은 파일과 이미 저장된 파일을 originName을 비교하여 새로 저장해아하는 파일 목록을 찾는다.
	      for (MultipartFile multipartFile : multipartList) { // 전달되어온 파일 하나씩 검사
	          // 파일의 원본명 얻어오기
	          String multipartOrigName = multipartFile.getOriginalFilename();
	          multipartListName.add(multipartOrigName);
	          if(!dbOriginNameList.contains(multipartOrigName)){   // DB에 없는 파일이면
	              //System.out.println("add file"+multipartOrigName);
	        	  addFileList.add(multipartFile); // DB에 저장할 파일 목록에 추가
	          }
	      }
	      // 전달받은 파일과 이미 저장된 파일의 originName을 비교하여 이미 저장된 파일 중 삭제되어야하는 파일을 확인하고 삭제한다.
	      for (MeetPhoto dbPhoto : dbPhotoList) {
	    	  String dboriginname = dbPhoto.getOrigFileName();
	    	  if(!multipartListName.contains(dboriginname)) {
	    		  System.out.println("photo delete "+dboriginname);
	              	System.out.println("photo"+dbPhoto.getFilePath().replace("\\", "/"));
	              	//실제 파일이 저장된 경로를 통해 실제 파일을 삭제한다.
	              	fileService.delete(dbPhoto.getFilePath().replace("\\", "/"));
	              	//데이터베이스에 저장된 정보를 삭제한다.
	              	meetphotoRepository.deleteMeetPhotoByFileId(dbPhoto.getId());
	    	  }
	      }
	      String thumbnail = null;
	      if(multipartListName.size()!=0) {
	    	  //만약 받은 파일이 1개 이상이면 썸네일을 저장한다.
	    	  thumbnail = multipartListName.get(0);
	      }
	      //System.out.println("thumbnail"+thumbnail);

        try {
        	//받은 정보들을 update한다.
			meetService.update(meetNo, requestDTO, addFileList,thumbnail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
        		

    }
    /***************************************************************************************************************************/
	//API : 모임 생성 이미지 포함
    //postman - meet/ 이미지 포함 모임 추가
    //모임 정보를 입력받아 모임을 생성한다. 이미지 또한 입력가능하다.
	@PostMapping("/meet/addwithImage")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody String joinimage(MeetImageVO meetImageVO)  throws Exception{
		//카테고리 이름을 받아서 category 로 변환
		Category meetCategory = categoryRepository.findByName(meetImageVO.getCategoryname());
		
		//System.out.println(meetCategory);
		//System.out.println(meetImageVO.getCategoryname());
		//사용자를 받아서 변환
		User hostuser = userRepository.findByUserNo(meetImageVO.getUserNo());
		// 참여자 제한을 리스트의 index로 받아오기 때문에 실제 사용자 제한 수로 변환한다. 무제한을 표현하기 위해 100으로 사용하였다.
		List<Integer> numbers = Arrays.asList(2, 4, 8,10, 100);
		//받은 정보들과 변환한 정보들
		MeetCreateRequestDTO requestDTO=
				MeetCreateRequestDTO.builder()
					.address(meetImageVO.getAddress())
					.approval(meetImageVO.isApproval())
					.age(meetImageVO.isAge())
					.category(meetCategory)
					.commentNum(0)
					.content(meetImageVO.getContent())
					.fee(meetImageVO.getFee())
					.lat(meetImageVO.getLat())
					.lon(meetImageVO.getLon())
					.peopleLimit(numbers.get(meetImageVO.getPeopleLimit()))
					.peopleNum(1)
					.personClosed(false)
					.time(meetImageVO.getTime())
					.timeClosed(false)
					.title(meetImageVO.getTitle())
					.user(hostuser)
					.build();

		// 모임을 저장하고 meetNo를 반환받는다.
		int meetNo = meetService.create(requestDTO,meetImageVO.getFiles());
		// TODO : meet add와 동시에 host user 참가
		// TODO : meed add 예외처리
		
		// 모임을 생성 후 host user(모임 생성한 사용자)를 자동으로 모임에 참가하도록 한다.
		Meet usermeet = meetRepository.findByMeetNo(meetNo);
		//System.out.println("meet"+usermeet.toString());
		if (usermeet == null) {
		    throw new IllegalStateException("Meet not found with meetNo: " + meetNo);
		}
		
		//모임 생성과 동시에 팔로워들에게 알림전송, 알림저장
		//모임 host를 follow하는 사람들 리스트를 가져온다
		List<Integer> followers = followRepository.selectFolloweeNo(hostuser.getUserNo());
		for (Integer followerNo : followers) {
			//한명씩 확인하면서
			//follower의 token을 가져와서
			String token = userRepository.selectToken(followerNo);
			//issue 모임 이미지가 없이 보내는 경우 
			//알림을 fcm으로 보낸다.
			fcmService.sendMessageTo(token, "새로운 건수", hostuser.getUsername()+"님이 새로운 건수를 추가했습니다",
					hostuser.getUsername(),null,String.valueOf(meetNo),"2",hostuser.getProfileImage());
			//System.out.println("send message to "+followerNo);
			//알림 데이터베이스에 알림을 저장한다. 
			Notification notice = new Notification();
			notice.setNotiType(2);
			User follower = userRepository.findByUserNo(followerNo);
			notice.setUser(follower);
			notice.setUserNumber(hostuser.getUserNo());
			notice.setName(hostuser.getUsername());
			notice.setMeetNumber(meetNo);
			notice.setImageLink(hostuser.getProfileImage());
			notificationRepository.save(notice);
		}
		
		//모임 host 자동 참가 구현
		MeetUser meetuser = MeetUser.builder()
				.meet(usermeet)
				.user(hostuser)
				.host(true)
				.build();
    	MeetUserRepository.save(meetuser);

		return "meet add!!";
	}
	/************************************************************************************************************************/
	//모임 제목 추출 api이다 . 사용하지 않는다.
	@GetMapping("/meet/title/{meetNo}")
	public @ResponseBody String selectTitleByMeetNo(@PathVariable("meetNo") int meetNo) {
		//Meet meet = meetRepository.findByMeetNo(meetNo);
		String meetTitle = meetRepository.findTitleByMeetNo(meetNo);
		System.out.println(meetTitle);
		//return "meet!";
		return meetTitle;
	}
	/*******************************************************************************************************************************************/
	//API : 모임 제목 리스트 - 탐색페이지 - 
	//postman meet/모임 제목 검색
	// sort, categoryName, page를 입력받는다.
	@PostMapping("/meet/titlelist/{title}")
    public ResponseEntity<List<MeetImageListDTO>> findByAddressLike(@PathVariable("title") String title,
    		@RequestBody SortCategoryDTO sortcategory) {
		String sort = sortcategory.getSort();
		String categoryName = sortcategory.getCategoryName();
		int categoryNo=0;
		//카테고리 이름을 변환한다.
		//만약 전체보기가 아니면 
		if(!categoryName.equals("전체보기")) {
			System.out.println("전체보기가 아니다");
			//카테고리 이름을 보고 categoryNo를 찾고
			Category category = categoryRepository.findByName(categoryName);
			//그런데 찾은 category가 없으면
			if(category == null) {
				//그냥 전체보기
				categoryNo = 0;
			}else {
				categoryNo = category.getCategoryNo();
			}
			System.out.println(categoryNo);
		}
		//categoryNo가 0이면 전체보기고 아니면 특정 카테고리 검색이다.
        List<MeetListDTO> meetList = meetRepository.findMeetListByTitle(title,categoryNo,sort,sortcategory.getPage());
        //모임의 이미지들을 함께 출력한다.
        List<MeetImageListDTO> list = new ArrayList<>();
        for( MeetListDTO meet : meetList) {
        	//모임 이미지 url들
    		ArrayList<String> meetImage = new ArrayList<String>();
    		// meetfile에서 meetNo를 이용해 file의 id들을 가져온 후
    		List<String> meetImageId =meetphotoRepository.selectIDMeetNo(meet.getMeetNo());
    		for( String id : meetImageId) {
    			//url로 만들어서 리스트로 저장한다.
    			String url = allurl+"meetimage/"+id;
    			meetImage.add(url);
    		}
    		//TODO 사용자 이미지 출력
    		List<UserNoImage> userProfileImage  = new ArrayList<>();
    		List<Object[]> userprofilelist = MeetUserRepository.selectUserNoProfileImageByMeetNo(meet.getMeetNo());
    		for(Object[] userprofile : userprofilelist) {
    			int userNo = (int) userprofile[0];
    			if(userNo == meet.getUserNo()) {
    				System.out.println("host");
    				continue;
    			}
    			UserNoImage u = new UserNoImage((int) userprofile[0],(String )userprofile[1]);
    			userProfileImage.add(u);
    		}
    		MeetImageListDTO a = new MeetImageListDTO(meet.getCategoryName(),meet.getTitle(),meet.getTime(),meet.getMeetNo(),meet.getUserNo(),meet.getUserProfileImage(),
    				meet.getUsername(),meet.getCommentNum(),meet.getPeopleLimit(),meet.isPeopleClosed(),meet.getPeopleNum(),meet.getLon(),meet.getLat(),meet.getAddress(),
    				meetImage,userProfileImage);
    		list.add(a);
        }
        return ResponseEntity.ok(list);
    }
	/***************************************************************************************************************************************/
	//API : 모임 카테고리 리스트 - 탐색페이지 - 
	//postman meet/탐색 카테고리
	@PostMapping("/meet/categorylist")
    public ResponseEntity<List<MeetImageListDTO>> findByCategory(
    		@RequestBody SortCategoryDTO sortcategory) {
		String sort = sortcategory.getSort();
		String categoryName = sortcategory.getCategoryName();
		
		int categoryNo=0;
		//만약 전체보기가 아니면 
		if(!categoryName.equals("전체보기")) {
			System.out.println("전체보기가 아니다");
			//카테고리 이름을 보고 categoryNo를 찾고
			Category category = categoryRepository.findByName(categoryName);
			//그런데 찾은 category가 없으면
			if(category == null) {
				//그냥 전체보기
				categoryNo = 0;
			}else {
				categoryNo = category.getCategoryNo();
			}
			//System.out.println(categoryNo);
		}
        List<MeetListDTO> meetList = meetRepository.findMeetListByCategory(categoryNo,sort,sortcategory.getPage());
        List<MeetImageListDTO> list = new ArrayList<>();
        for( MeetListDTO meet : meetList) {
        	//모임 이미지 url들
    		ArrayList<String> meetImage = new ArrayList<String>();
    		// meetfile에서 meetNo를 이용해 file의 id들을 가져온 후
    		List<String> meetImageId =meetphotoRepository.selectIDMeetNo(meet.getMeetNo());
    		for( String id : meetImageId) {
    			//url로 만들어서 리스트로 저장한다.
    			String url = allurl+"meetimage/"+id;
    			meetImage.add(url);
    		}
    		//TODO 사용자 이미지 출력
    		List<UserNoImage> userProfileImage  = new ArrayList<>();
    		List<Object[]> userprofilelist = MeetUserRepository.selectUserNoProfileImageByMeetNo(meet.getMeetNo());
    		for(Object[] userprofile : userprofilelist) {
    			int userNo = (int) userprofile[0];
    			if(userNo == meet.getUserNo()) {
    				System.out.println("host");
    				continue;
    			}
    			UserNoImage u = new UserNoImage((int) userprofile[0],(String )userprofile[1]);
    			userProfileImage.add(u);
    		}
    		MeetImageListDTO a = new MeetImageListDTO(meet.getCategoryName(),meet.getTitle(),meet.getTime(),meet.getMeetNo(),meet.getUserNo(),meet.getUserProfileImage(),
    				meet.getUsername(),meet.getCommentNum(),meet.getPeopleLimit(),meet.isPeopleClosed(),meet.getPeopleNum(),meet.getLon(),meet.getLat(),meet.getAddress(),
    				meetImage,userProfileImage);
    		list.add(a);
        }
        return ResponseEntity.ok(list);
    }
	/*************************************************************************************************************************************/
	// API : 모임 주소 리스트 - 
	// postman meet/ MeetList-주소
	//특이사항 : 다른 모임 리스트 출력들과 달리 categoryName을 리스트 형식으로 받는다.
	@PostMapping("/meet/list/{address}")
    public  ResponseEntity<List<MeetImageListDTO>> findByAdd(@PathVariable("address") String address,
    		@RequestBody SortCategoryListDTO sortcategory) {
		//정렬방법
		String sort = sortcategory.getSort();
		//카테고리
		List<String> categoryNameList = sortcategory.getCategoryName();
		
		List<Integer> categoryNo = new ArrayList<>();
		//System.out.println(categoryName);
		for (String categoryName : categoryNameList) {
			//만약 전체보기가 아니면 
			if(!categoryName.equals("전체보기")) {
				//카테고리 이름을 보고 categoryNo를 찾고
				Category category = categoryRepository.findByName(categoryName);
				//그런데 찾은 category가 없으면
				if(category == null) {
					//잘못된 값
				}else {
					int categoryNo1 = category.getCategoryNo();
					categoryNo.add(categoryNo1);
				}
				//System.out.println(categoryNo);

			}else {
				categoryNo =null;
				break;
			}
		}
		
		System.out.println(categoryNo);
		//주소를 이용해서 모임 리스트 출력
        List<MeetListDTO> meetList = meetRepository.findMeetListByAddress(address,sort,sortcategory.getPage(),categoryNo);
        
        List<MeetImageListDTO> list = new ArrayList<>();
        for( MeetListDTO meet : meetList) {
        	//모임 이미지 url들
    		ArrayList<String> meetImage = new ArrayList<String>();
    		// meetfile에서 meetNo를 이용해 file의 id들을 가져온 후
    		List<String> meetImageId =meetphotoRepository.selectIDMeetNo(meet.getMeetNo());
    		for( String id : meetImageId) {
    			//url로 만들어서 리스트로 저장한다.
    			String url = allurl+"meetimage/"+id;
    			meetImage.add(url);
    		}
    		//TODO 사용자 이미지 출력
    		List<UserNoImage> userProfileImage  = new ArrayList<>();
    		List<Object[]> userprofilelist = MeetUserRepository.selectUserNoProfileImageByMeetNo(meet.getMeetNo());
    		for(Object[] userprofile : userprofilelist) {
    			int userNo = (int) userprofile[0];
    			if(userNo == meet.getUserNo()) {
    				System.out.println("host");
    				continue;
    			}
    			UserNoImage u = new UserNoImage((int) userprofile[0],(String )userprofile[1]);
    			userProfileImage.add(u);
    		}
    		MeetImageListDTO a = new MeetImageListDTO(meet.getCategoryName(),meet.getTitle(),meet.getTime(),meet.getMeetNo(),meet.getUserNo(),meet.getUserProfileImage(),
    				meet.getUsername(),meet.getCommentNum(),meet.getPeopleLimit(),meet.isPeopleClosed(),meet.getPeopleNum(),meet.getLon(),meet.getLat(),meet.getAddress(),
    				meetImage,userProfileImage);
    		list.add(a);
        }
        return ResponseEntity.ok(list);
    }
	/**********모임 전체 리스트************/
	@PostMapping("/meet/listall")
    public ResponseEntity<List<MeetListDTO>> findByMeetListAll(
    		@RequestBody String sort) {
        List<MeetListDTO> meetList = meetRepository.findMeetListByAll(sort);
        return ResponseEntity.ok(meetList);
    }
	/*********************************************************************************************************************************************/
	//API: 모임 주최 리스트 - 
	//postman meet/주최한 모임 출력
	//다른 리스트와 달리 category 가 없다.
	@PostMapping("/meet/hostlist/{userNo}")
    public ResponseEntity<List<MeetImageListDTO>> findByHostNo(@PathVariable("userNo") int userNo,
    		@RequestBody SortPageDTO sort) {
        List<MeetListDTO> meetList = meetRepository.findMeetListByUserNo(userNo, sort.getSort(), sort.getPage());
        List<MeetImageListDTO> list = new ArrayList<>();
        for( MeetListDTO meet : meetList) {
        	//모임 이미지 url들
    		ArrayList<String> meetImage = new ArrayList<String>();
    		// meetfile에서 meetNo를 이용해 file의 id들을 가져온 후
    		List<String> meetImageId =meetphotoRepository.selectIDMeetNo(meet.getMeetNo());
    		for( String id : meetImageId) {
    			//url로 만들어서 리스트로 저장한다.
    			String url = allurl+"meetimage/"+id;
    			meetImage.add(url);
    		}
    		//TODO 사용자 이미지 출력
    		List<UserNoImage> userProfileImage  = new ArrayList<>();
    		List<Object[]> userprofilelist = MeetUserRepository.selectUserNoProfileImageByMeetNo(meet.getMeetNo());
    		for(Object[] userprofile : userprofilelist) {
    			int userNo1 = (int) userprofile[0];
    			if(userNo1 == meet.getUserNo()) {
    				System.out.println("host");
    				continue;
    			}
    			UserNoImage u = new UserNoImage((int) userprofile[0],(String )userprofile[1]);
    			userProfileImage.add(u);
    		}
    		MeetImageListDTO a = new MeetImageListDTO(meet.getCategoryName(),meet.getTitle(),meet.getTime(),meet.getMeetNo(),meet.getUserNo(),meet.getUserProfileImage(),
    				meet.getUsername(),meet.getCommentNum(),meet.getPeopleLimit(),meet.isPeopleClosed(),meet.getPeopleNum(),meet.getLon(),meet.getLat(),meet.getAddress(),
    				meetImage,userProfileImage);
    		list.add(a);
        }
        return ResponseEntity.ok(list);
    }
	/*********************************************************************************************************************************************/
	//API: 모임 참가 리스트 - 
	//postman meet/참가한 모임 
	//다른 리스트와 달리 category 가 없다.
	@PostMapping("/meet/participate-list/{userNo}")
    public ResponseEntity<List<MeetImageListDTO>> findByUserNo(@PathVariable("userNo") int userNo,
    		@RequestBody SortPageDTO sort) {
        List<MeetListDTO> meetList = meetRepository.findMeetPartListByUserNo(userNo, sort.getSort(), sort.getPage());
        List<MeetImageListDTO> list = new ArrayList<>();
        for( MeetListDTO meet : meetList) {
        	//모임 이미지 url들
    		ArrayList<String> meetImage = new ArrayList<String>();
    		// meetfile에서 meetNo를 이용해 file의 id들을 가져온 후
    		List<String> meetImageId =meetphotoRepository.selectIDMeetNo(meet.getMeetNo());
    		for( String id : meetImageId) {
    			//url로 만들어서 리스트로 저장한다.
    			String url = allurl+"meetimage/"+id;
    			meetImage.add(url);
    		}
    		//TODO 사용자 이미지 출력
    		List<UserNoImage> userProfileImage  = new ArrayList<>();
    		List<Object[]> userprofilelist = MeetUserRepository.selectUserNoProfileImageByMeetNo(meet.getMeetNo());
    		for(Object[] userprofile : userprofilelist) {
    			int userNo1 = (int) userprofile[0];
    			if(userNo1 == meet.getUserNo()) {
    				System.out.println("host");
    				continue;
    			}
    			UserNoImage u = new UserNoImage((int) userprofile[0],(String )userprofile[1]);
    			userProfileImage.add(u);
    		}
    		MeetImageListDTO a = new MeetImageListDTO(meet.getCategoryName(),meet.getTitle(),meet.getTime(),meet.getMeetNo(),meet.getUserNo(),meet.getUserProfileImage(),
    				meet.getUsername(),meet.getCommentNum(),meet.getPeopleLimit(),meet.isPeopleClosed(),meet.getPeopleNum(),meet.getLon(),meet.getLat(),meet.getAddress(),
    				meetImage,userProfileImage);
    		list.add(a);
        }
        return ResponseEntity.ok(list);
    }
	/*********************************************************************************************************************************/
	@Value("${url}")
	private String allurl;
	//API : 모임 상세페이지 - 
	//postman meet/모임상세
	@PostMapping("/meet/detail/{meetNo}")
    public  ResponseEntity<MeetDetailDTO> findByAddressLike(@PathVariable("meetNo") int meetNo
    		,@RequestBody  UserNoDTO userNoDTO) {
		//상세페이지 하단에 출력할 댓글(3개)
		List<Object[]> c = meetRepository.selectCommentSomeByMeetNo(meetNo);
		//모임 상세정보
		List<Object[]> meetDetail = meetRepository.findByDetailMeetNo(meetNo);
		//System.out.println(Arrays.toString(meetDetail));
		//참가한 사람들의 정보
		List<Object[]> users = meetRepository.findListMeetUser(meetNo);
		//int hostNo = meetRepository.findHostByMeetNo(meetNo);
		//현재 접속한 사람의 userNo
		int userNo = userNoDTO.getUserNo();
        //System.out.println(userNo);
		//현재 접속한 사람이 모임에 참가했는지 확인
		MeetUser meetuser = MeetUserRepository.findByUserNoAndMeetNo1(userNo,meetNo);
		boolean isInsert;
		if(meetuser==null) {
			isInsert=false;
		}else {
			isInsert=true;
		}
		//System.out.println(isInsert);
		//모임 이미지 url들
		ArrayList<String> meetImage = new ArrayList<String>();
		// meetfile에서 meetNo를 이용해 file의 id들을 가져온 후
		List<String> meetImageId =meetphotoRepository.selectIDMeetNo(meetNo);
		for( String id : meetImageId) {
			//url로 만들어서 리스트로 저장한다.
			String url = allurl+"meetimage/"+id;
			meetImage.add(url);
		}
		// 모임 상세, 카테고리, 참가자 리스트, 참가여부, 모임 이미지를 다 모아서 하나의 data로 묶는다.
		MeetDetailDTO meetDetaildto = meetService.convertToMeetDetailDTO(meetDetail,c,users,isInsert,meetImage,userNo);
		
		///System.out.println(meetImage);
		return ResponseEntity.ok(meetDetaildto);
		
    }
	/******************************************************************************************************************************/
	//API : 모임 댓글 리스트 -
	//postman - meet/댓글리스트
	//특정 모임의 댓글 전체를 가져온다. 
	//신고 처리된 댓글은 "신고된 댓글입니다."가 출력된다.
	//meetCommentNo, meetNo, parentNo(부모 댓글 없으면 -1), content, userNo, userProfileImage, username, createDate
	@GetMapping("meet/comment/{meetNo}")
	public @ResponseBody List<MeetCommentDTO> findByComments(@PathVariable("meetNo") int meetNo) {
		List<Object[]> c = meetRepository.selectCommentByMeetNo(meetNo);
		List<MeetCommentDTO> comments = meetService.convertToMeetComment(c);
		return comments;
	}
	/******************************************************************************************************************************/
	//API : 모임 댓글 리스트 - 차단 기능 추가
	//postman - meet/댓글리스트-차단추가
	//특정 모임의 댓글 전체를 가져온다. 
	//신고 처리된 댓글은 내용에 "신고된 댓글입니다."가 출력된다.
	//차단된 사용자의 경우 내용에 "차단된 사용자입니다"가 출력된다
	//meetCommentNo, meetNo, parentNo(부모 댓글 없으면 -1), content, userNo, userProfileImage, username, createDate
	@GetMapping("meet/comment")
	public @ResponseBody List<MeetCommentDTO> findByCommentsBlock(@RequestParam("userNo") int userNo, @RequestParam("meetNo") int meetNo) {
		List<Object[]> c = meetRepository.selectCommentByMeetNo(meetNo);
		List<MeetCommentDTO> comments = meetService.convertToMeetCommentBlock(c,userNo);
		return comments;
	}
	/****************모임변경 ************/
	@PutMapping("/moidfymeet/{meetNo}")
	public ResponseEntity<String> modifyUser(
			@PathVariable("meetNo") int meetNo,
			@RequestBody Meet meetReq) {
		//System.out.println("userNo :"+userNo);
		try{
			meetService.updateMeet(meetNo,meetReq.getTitle(),meetReq.getContent(),meetReq.getPeopleLimit());
			return ResponseEntity.ok("modify meet success!!!");
		}
		catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("modify meet error!!");
	    }
	}
	
	/*********************************************************************************************************************************************/
	//API 모임참가 - 
	//postman meet/참가
	//특이사항 : { "meet" : { "meetNo": - },"user":{"userNo": - }) 처럼 감싼 상태로 요청해야한다.
	//모임에 참가한다. 이미 참가한 상태면 already added 를 반환한다. 만약 모임이 닫긴 상태이면 참가할 수 없다.
	@PostMapping("/meetuseradd")
	public ResponseEntity<String> join(@RequestBody MeetUser meetUser) {
	    try {
	    	//참가하려는 사용자가 모임을 주최한 호스트인 경우 host를 true로 저장한다. 다만 이 경우 모임을 생성하면서 자동으로 호스트를 참가하도록 추가하기 때문에 사용되지 않는다.
	        int hostNo = meetRepository.findUserNoByMeetNo((int)meetUser.getMeet().getMeetNo());
	        //System.out.println(meetUser.getUser().getUserNo());
	        //System.out.println(hostNo);
	        if (meetUser.getUser().getUserNo() == hostNo) {
	        	//System.out.println("Host!!");
	            meetUser.setHost(true);
	        }
	        //입력받은 userNo와 meetNo를 이용하여 기존에 참가한 사용자인지 판단한다.
	        MeetUser meetuser = MeetUserRepository.findByUserNoAndMeetNo1(meetUser.getUser().getUserNo(), meetUser.getMeet().getMeetNo());
	        int meetNo = meetUser.getMeet().getMeetNo();
	        //System.out.println(meetNo);
	        Meet meet = meetRepository.findByMeetNo(meetNo);
	        //System.out.println(meetNo);
	        //참가하려는 모임이 닫긴 상태인지 확인한다.
	        boolean closed = meet.isPersonClosed();
	        //System.out.println(closed);
	        if(closed) {
	        	//만약 닫긴 상태이면 참가하지 못하고 closed!!를 반환한다.
	        	return ResponseEntity.ok("closed!!!");
	        }else {
	        	//닫기지 않고 기존에 참가하지 않았다면
	        	if(meetuser==null) {
		        	//System.out.println(meetNo);
	        		//참가 테이블에 저장한다.
		        	MeetUserRepository.save(meetUser);
		        	//System.out.println(meetNo);
		        	//모임 테이블의 참가자 수를 +1 한다.
			        meetRepository.updateUserMeetPlus(meetUser.getMeet().getMeetNo());
		        	//System.out.println("meetuseradd success!!");
			        //System.out.println(meetUser);
			        //만약 모임의 참가자수를 +1을 하였는데 limit에 일치한다면. 해당 참가자의 참가를 마지막으로 참가자 수 마감이 된다.
			        if((meet.getPeopleNum()+1 )== meet.getPeopleLimit()) {
			        	//모임의 참가자 수 마감을 true로 한다.
			        	meetRepository.updateMeetLimitTrue(meetNo);
			        }
			        return ResponseEntity.ok("add meet user success!!!");
			        }
		        else {
		        	//기존에 참가한 사용자라면
		        	return ResponseEntity.ok("already added!!!");
		        }
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("add meet user error!!");
	    }
	}
	/*****************************************************************************************************************************************/
	//API 모임탈퇴 - 
	//postman meet/ 모임참가취소
	//참가한 모임을 탈퇴한다. 입력은 모임 참가와 동일하다.
	@PostMapping("/meetuseremove")
	public @ResponseBody String remove(@RequestBody MeetUser meetUser) {
	    try {
	    	//일단 참가 테이블에서 탈퇴하려는 사용자와 모임이 있는지 확인한다.
	    	MeetUser meetuser = MeetUserRepository.findByUserNoAndMeetNo1(meetUser.getUser().getUserNo(), meetUser.getMeet().getMeetNo());
	    	//탈퇴하고자 하는 참가자와 모임이 있다면
	        if(meetuser!=null) { 
	        	int meetNo = meetuser.getMeet().getMeetNo();
		    	int userNo = meetuser.getUser().getUserNo();
		        //System.out.println(Integer.toString(meetNo));
		        //System.out.println(meetuser.getMeetUserNo());
	        	//모임 참가 정보 삭제
	        	meetUserService.deleteMeetUser(userNo,meetNo);
	        	//모임 테이블 참가자-1
	        	meetRepository.updateUserMeetMinus(meetNo);
	        	Meet meet = meetRepository.findByMeetNo(meetNo);
	        	Integer limit = meet.getPeopleLimit();
	        	Integer pNum = meet.getPeopleNum();
	        	//personClosed = false 참가자 수 마감이 해제된다.
	        	meetRepository.updateMeetLimitFalse(meetNo);
	        	
	        	//System.out.println(meetUser);
	        	return "done delete meetUser!";
	        }else {
	        	//만약 탈퇴하고자 하는 참가자와 모임이 없다면
	        	return "no data delete meetUser";
	        }
	        
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "error occurred while adding meetUser";
	    }
	}
	/****************************************************************************************************************************/
	//API 댓글 작성 - 
	//postman - meet/댓글추가
	//입력 : meetNo , user-userNo, parentNo(필수는 아니다 없다면 자동으로 -1) content
	@PostMapping("/meetcommentadd")
	public ResponseEntity<String> join(@RequestBody MeetComment meetComment) {
		
		//System.out.println(meetComment);
		//meetRepository.save(meet);
		try {
			//부모 댓글이 없다면 -1
			if(meetComment.getParentNo()==null) {
				meetComment.setParentNo(-1);
			}
			//댓글을 저장한다.
			meetCommentRepository.save(meetComment);
	        //System.out.println(meetUser); 
			//댓글이 달린 모임을 찾은 후
			Meet meet = meetRepository.findByMeetNo(meetComment.getMeetNo());
			//참가자 정보를 가져온다.
			List<User> userList= MeetUserRepository.selectByMeetNo(meetComment.getMeetNo());
			//참가자에게 한명씩 알림을 보낸다.
			for (User userNo : userList) {
				if(userNo.getUserNo()== meetComment.getUser().getUserNo()) {
					continue;//댓글 작성자에게는 알림이 가지 않음
				}
				String token = userNo.getToken();
				String imageLink = meet.getMeetImage();
				if(meet.getMeetImage()==null) {
					imageLink = allurl+"meetimage/0";
				}
				fcmService.sendMessageTo(token, "새로운 댓글", meet.getTitle()+"에 새로운 댓글이 달렸습니다.",
						meet.getTitle(),null,String.valueOf(meet.getMeetNo()),"6",imageLink);
				System.out.println("send message to "+userNo.getUserNo());
				//보낸 알림을 받는 사람의 Notification테이블에 저장한다.
				Notification notice = new Notification(userNo,6,null,meet.getMeetNo(),meet.getTitle(),imageLink);
				notificationRepository.save(notice);
			}
			//모임의 댓글 수 +1
			meetRepository.updateCommentPlus(meetComment.getMeetNo());
			return ResponseEntity.ok("add comment success!!!");
	    }catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("add comment error!!");
	    }
	}
}
