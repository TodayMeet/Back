package com.cos.todaymeet.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cos.todaymeet.DTO.UserCreateRequestDTO;
import com.cos.todaymeet.DTO.UserJoinCreateRequestDTO;
import com.cos.todaymeet.DTO.UserProfileDTO;
import com.cos.todaymeet.Mapper.ImageMapper;
import com.cos.todaymeet.Service.PhotoService;
import com.cos.todaymeet.Service.UserService;
import com.cos.todaymeet.config.auth.PrincipalDetails;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.model.UserCategory;
import com.cos.todaymeet.model.UserFIlevO;
import com.cos.todaymeet.model.UserJoinFIlevO;
import com.cos.todaymeet.repository.CategoryRepository;
import com.cos.todaymeet.repository.PhotoRepository;
import com.cos.todaymeet.repository.UserCategoryRepository;
import com.cos.todaymeet.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCategoryRepository usercategoryRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserService userService;
	
	/*********회원가입*************/
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody User user) {
//		System.out.println(user.toString());
//		String username = newuser.getUsername();
//		String rawPassword = newuser.getUserpassword();
//		String email = newuser.getUseremail();
//		User user = new User();
		//user.setUsername();
		//user.setEmail(email);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		try{
			userRepository.save(user);//패스워드 암호화 안됨 -> 시큐리티로 불가능
			return ResponseEntity.ok("join success!!!");
		} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("join error!!");
	    }
		//return "done!!";
	}
	/*************로그인***********************/
	@PostMapping("/delete-user")
	public ResponseEntity<String> delete(@RequestBody User user) {
	    String email = user.getEmail();
	    String rawPassword = user.getPassword();
	    String encodedPassword = userRepository.selectPassword(email);
	    System.out.println(encodedPassword);
	    if (encodedPassword != null) {
	        boolean passwordMatches = bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	        if (passwordMatches) {
	            int userNo = userRepository.selectUserNo(email);
	            String userNoStr = Integer.toString(userNo);
	            userRepository.deleteUser(userNo);
	            return ResponseEntity.ok(userNoStr);
	        }
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error!!");
	}
	/**********************************************************************/
	//API 탈퇴 userNo
	@PostMapping("/delete-userno/{userNo}")
	public ResponseEntity<String> delete(@PathVariable("userNo") int userNo) {
	    
	    
	        //boolean passwordMatches = bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	        
	            
	            String userNoStr = Integer.toString(userNo);
	            userRepository.deleteUser(userNo);
	            return ResponseEntity.ok(userNoStr);
	        
	    
	    //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error!!");
	}
	/*************로그인***********************/
	//API 로그인
	@PostMapping("/loginB")
	public ResponseEntity<String> TestTest(@RequestBody User user) {
	    String email = user.getEmail();
	    String rawPassword = user.getPassword();
	    String encodedPassword = userRepository.selectPassword(email);
	    System.out.println(encodedPassword);
	    if (encodedPassword != null) {
	        boolean passwordMatches = bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	        if (passwordMatches) {
	            int userNo = userRepository.selectUserNo(email);
	            User u = userRepository.findByUserNo(userNo);
	            if(u.isDeleted()==true) {
	            	return ResponseEntity.ok("탈퇴한 회원입니다.");
	            }
	            String userNoStr = Integer.toString(userNo);
	            return ResponseEntity.ok(userNoStr);
	        }
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error!!");
	}
	/*****************password 변경********************/
	@PostMapping("/password/{userNo}")
	public ResponseEntity<String> modifyUserPassword(
	        @PathVariable("userNo") int userNo,
	        @RequestBody String password) {
	    try {
	    	org.json.JSONObject json = new org.json.JSONObject(password);
	        String passwordValue = json.getString("password");
	        System.out.println("password: " + passwordValue);
	    	//String rawPassword = password.getPassword();
	        String encPassword = bCryptPasswordEncoder.encode(passwordValue);
	        System.out.println("변경된 비밀번호: " + encPassword);
	        //User user = userRepository.findByUserNo(userNo);
	        //user.setPassword(encPassword);
	        userRepository.updatePassword(encPassword,userNo); // 변경된 비밀번호를 저장

	        return ResponseEntity.ok("password modify success!!!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password modify error!!");
	    }
	}
	@GetMapping("/login/test")
	public @ResponseBody String loginTest(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) {
		
		if (authentication != null) {
	        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
	        System.out.println(principalDetails.getUser());
	    }

	    if (userDetails != null) {
	        System.out.println(userDetails.getUser());
	    }

	    return "loginTest";
	}
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth
			) {
		
		OAuth2User oauth2User=(OAuth2User) authentication.getPrincipal();
		System.out.println(oauth2User.getAttributes());
		//사용자 정보를 받을 수 있다.
		System.out.println(oauth.getAttributes());
		return "OAuth loginTest";
	}
	@GetMapping("/")
	public @ResponseBody String index() {
		return "index";
	}
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println(principalDetails.getUser());
		return "user";
	}
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
//	@PostMapping("/join")
//	public String join(User user) {
//		System.out.println(user.toString());
//		user.setRole("ROLE_USER");
//		String rawPassword = user.getPassword();
//		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//		user.setPassword(encPassword);
//		userRepository.save(user);//패스워드 암호화 안됨 -> 시큐리티로 불가능
//		return "redirect:/login";
//	}
	

	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	@GetMapping("/joinProc")
	public @ResponseBody String joinProoc() {
		return "joinProc";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/test")
	public @ResponseBody String test() {
		return "test";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/test2")
	public @ResponseBody String test2() {
		return "test2";
	}
	//login
	
	/***************************************************************************************************************/
	//API 휴대전화 중복확인
	@GetMapping("/phone/{phoneNumber}")
	public ResponseEntity<String>  checkPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
		boolean check = userRepository.existsByPhoneNumber(phoneNumber);
		if(!check){
			return ResponseEntity.ok("phone number not duplicate!!!");
		}else {
			//String email = userRepository.findEmail
			return ResponseEntity.ok("phone number duplicate!!");
		}
	}
	/***************************************************************************************************************/
	//API 휴대전화 중복확인
	@GetMapping("/find-email/{phoneNumber}")
	public ResponseEntity<String>  findEmail(@PathVariable("phoneNumber") String phoneNumber) {
		boolean check = userRepository.existsByPhoneNumber(phoneNumber);
		if(!check){
			return ResponseEntity.ok("phone number not duplicate!!!");
		}else {
			String email = userRepository.findEmail(phoneNumber);
			return ResponseEntity.ok(email);
		}
	}
	/**************************************************************************************************************/
	//API 이메일 중복 확인
	@GetMapping("/email/{email}")
	public  ResponseEntity<String> checkEmail(@PathVariable("email") String email) {
		boolean check= userRepository.existsByEmail(email);
		if(!check){
			return ResponseEntity.ok("email not duplicate!!!");
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("email duplicate!!");
		}
	}
	/****************************************************************************************************************/
	//API username중복확인
	@GetMapping("/username/{username}")
	public  ResponseEntity<String> checkUserName(@PathVariable("username") String username) {
		boolean check = userRepository.existsByUsername(username);
		if(!check){
			return ResponseEntity.ok("username not duplicate!!!");
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username duplicate!!");
		}
	}
	
//	@PutMapping("/moidfy/{userNo}")
//	public void modifyUser(
//			@PathVariable("userNo") Integer userNo,
//			@RequestBody String phoneNumber) {
//		userRepository.updatePhoneNumber(phoneNumber,userNo);
//	}
	@Autowired
	private ImageMapper imageMapper;
	/********************************************************************************************************************************/
	//API 사용자 프로필 변경
	//TODO : 사용자 프로필 변경 이미지
	 @PostMapping("/user-profile/{userNo}")
	    @ResponseStatus(HttpStatus.CREATED)
	    public @ResponseBody String create(@PathVariable("userNo") int userNo,UserFIlevO userFileVO) throws Exception {
		// Member id로 조회하는 메소드 존재한다고 가정하에 진행

	    UserCreateRequestDTO requestDto = 
	    		UserCreateRequestDTO.builder()
	            			     .username(userFileVO.getUsername())
	            			     .gender(userFileVO.getGender())
	            			     .birth(userFileVO.getBirth())
	            			     .build();
	    
	    userService.create(requestDto, userFileVO.getFiles(),userNo);
	        return "image test" ;
	    }
		/********************************************************************************************************************************/
		//API multipart로 로그인
		//
		 @PostMapping("/joinB")
		    @ResponseStatus(HttpStatus.CREATED)
		    public  ResponseEntity<String> createUserWithMultipart(UserJoinFIlevO userFileVO) throws Exception {
				String rawPassword = userFileVO.getPassword();
				String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		    UserJoinCreateRequestDTO requestDto = 
		    		UserJoinCreateRequestDTO.builder()
		    						.email(userFileVO.getEmail())
		    						.password(encPassword)
		            			     .username(userFileVO.getUsername())
		            			     .gender(userFileVO.getGender())
		            			     .birth(userFileVO.getBirth())
		            			     .build();
		    System.out.println("----------------------------------");
		    System.out.println(userFileVO.getFiles());
		    userService.createUser(requestDto, userFileVO.getFiles());
		        return ResponseEntity.ok("add user success!!!");
		    }	 
	 /*********이미지출력******************/

	    
	/**************사용자 프로필 변경,추가*******************/
	@PostMapping("/user-profile1/{userNo}")
	public  ResponseEntity<String> modifyUser(
			@PathVariable("userNo") int userNo,
	        @RequestBody UserProfileDTO userprofile) {
	    System.out.println("userNo: " + userNo);
	    try {
//	        // 파일 업로드 처리
//	        ImageVO imageVO = new ImageVO();
//	        imageVO.setMimetype(file.getContentType());
//	        imageVO.setOriginal_name(file.getOriginalFilename());
//	        imageVO.setData(file.getBytes());
//	        imageMapper.insertBoard(imageVO);
	    	String username = userprofile.getUsername();
	    	Integer gender = userprofile.getGender();
	    	
	    	Timestamp birth = userprofile.getBirth();
	        // 사용자 정보 업데이트
	        userService.updateUser(userNo, username, gender, birth);
	        return ResponseEntity.ok("Profile modified successfully!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to modify profile!");
	    }
//			@PathVariable("userNo") int userNo,
//			@RequestBody User userReq) {
//		System.out.println("userNo :"+userNo);
//		try {
//			userService.updateUser(userNo, userReq.getUsername(), userReq.getGender(), userReq.getBirth(), userReq.getProfileImage());
//			return ResponseEntity.ok("profile modify success!!!");
//		} catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("profile modify error!!");
	    
	}
	/***********************************************************************************************************************/
	//API 사용카 카테고리 변경
	//TODO 사옹자 카테고리 변경 잘못딘 값 처리
	@PutMapping("/usercategory/{userNo}")
	public  ResponseEntity<String> modifyUserCategory(
			@PathVariable("userNo") int userNo,
			@RequestBody ArrayList<String> categoryList) {
		//System.out.println("categoryList :"+categoryList.toString());
		try{
			userService.modifyUserCategory(userNo, categoryList);
			return ResponseEntity.ok("category modify success!!!");
		} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("category modify error!!");
	    }
	}
	/***********************************************************************************************************************/
	//API 사용카 카테고리 출력
	//TODO 사옹자 카테고리 변경 잘못딘 값 처리
	@GetMapping("/usercategory/{userNo}")
	public  ResponseEntity<List<String>> selectCateogry(
			@PathVariable("userNo") int userNo) {
		//System.out.println("categoryList :"+categoryList.toString());
		try{
			List<UserCategory> usecategory = usercategoryRepository.findByUserNo(userNo);
			//userService.modifyUserCategory(userNo, categoryList);
			List<String> output = new ArrayList<>();
			for (UserCategory category : usecategory) {
				int categoryNo = category.getCategoryNo();
				categoryRepository.findByCategoryNo(categoryNo);
				output.add(categoryRepository.findByCategoryNo(categoryNo).getName());
			}
			return ResponseEntity.ok(output);
		} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
	}
	/**********************************************************************************************************************/
	//API 사용자 프로필 출력
	@GetMapping("/user-profile/{userNo}")
	public @ResponseBody UserProfileDTO findUserProfileByUserNo(@PathVariable("userNo") int userNo) {
		List<Object[]> c = userRepository.selectUserProfile(userNo);
		System.out.println(c);		
		UserProfileDTO userProfileDTO = userService.convertUserProfileDTO(c);
		return userProfileDTO;
	}
	@Autowired 
	private PhotoRepository photoRepository;
	@Autowired 
	private PhotoService photoService;

//	@CrossOrigin
//    @GetMapping(
//            value = "/user-profileimage/{userNo}"
////            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
//    )
//    public ResponseEntity<UserProfileAllDTO> getImage1(@PathVariable int userNo) throws IOException {
//    	Long id = photoRepository.test(userNo);
//        PhotoDTO photoDto = photoService.findByFileId(id);
//        String absolutePath
//        	= new File("").getAbsolutePath() + File.separator + File.separator;
//        String path = photoDto.getFilePath();
//        System.out.println("21");
//        InputStream imageStream = new FileInputStream(absolutePath + path);
//        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
//        imageStream.close();
//        //System.out.println("2");
//        User user = userRepository.findByUserNo(userNo);
//        //System.out.println("2");
//        //System.out.println("user"+user.getUsername());
//        UserProfileAllDTO userProfileAll = new UserProfileAllDTO(imageByteArray,user.getUsername(),user.getGender(),user.getBirth());
//        return ResponseEntity.ok(userProfileAll);
//    }

}
