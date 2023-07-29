package com.cos.todaymeet.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.todaymeet.DTO.UserCreateRequestDTO;
import com.cos.todaymeet.DTO.UserJoinCreateRequestDTO;
import com.cos.todaymeet.DTO.UserProfileDTO;
import com.cos.todaymeet.model.Category;
import com.cos.todaymeet.model.Photo;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.model.UserCategory;
import com.cos.todaymeet.repository.CategoryRepository;
import com.cos.todaymeet.repository.PhotoRepository;
import com.cos.todaymeet.repository.UserCategoryRepository;
import com.cos.todaymeet.repository.UserFileHandler;
import com.cos.todaymeet.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
	@Autowired 
	private final UserRepository userRepository;
	//imgae
	@Autowired
	private final PhotoRepository photoRepository;
	@Autowired
	private final PhotoService photoService;
	@Autowired
	private final fileService fileService;
	@Autowired
    private final UserFileHandler fileHandler;	
	@Value("${url}")
	private String allurl;
	@Transactional
    public int createUser(
    	UserJoinCreateRequestDTO requestDto,
        MultipartFile files
    ) throws Exception {
	// 파일 처리를 위한 Board 객체 생성
       User user = new User();
       String email = requestDto.getEmail();
       String password = requestDto.getPassword();
       user.setEmail(email);
       user.setPassword(password);
       String username = requestDto.getUsername();
       Integer gender =requestDto.getGender();
       Timestamp birth = requestDto.getBirth();
       Photo photoList = fileHandler.parseFileInfo1(files);
       if(photoList!=null) {
    	   System.out.println("=================================================");
    	   System.out.println(photoList.getOrigFileName());
    	   Photo p = photoRepository.save(photoList);
    	   user.setProfileImage(allurl+"imagetest/"+p.getId());
    	   user.addPhoto(p);
       }
    	   
        
        if (user != null) {
        	if(username!=null) {
        		user.setUsername(username);
        	}
        	if(gender!=null) {
        		user.setGender(gender);
        	}
        	if(birth!=null) {
        		user.setBirth(birth);
        	}
//        	if(photoList!=null) {
//        		user.addPhoto(photoRepository.save(photoList));
//        		Long id = photoRepository.test(userNo);
//        		//PhotoDTO photoDto = photoService.findByFileId(id);
//                String absolutePath
//                	=allurl+"imagetest/"+id.toString();
//                //String path = photoDto.getFilePath();
//        		user.setProfileImage(absolutePath);
//        	}
        	
        }
        return userrepository.save(user).getUserNo();
    }
	
	@Transactional
    public int create(
    	UserCreateRequestDTO requestDto,
        MultipartFile files, int userNo
    ) throws Exception {
	// 파일 처리를 위한 Board 객체 생성
       User user = userRepository.findByUserNo(userNo);
       String username = requestDto.getUsername();
       Integer gender =requestDto.getGender();
       Timestamp birth = requestDto.getBirth();
       List<Photo> dbphotos = photoRepository.gUserNo(userNo);
       System.out.println(dbphotos);
       //만약 받은 파일이 없으면
       if(files.isEmpty()) {
    	   if(dbphotos.size()==0) {
    		   System.out.println("xx");
    		   //그대로
    	   }else {
    		   System.out.println("xo");
    		   //기존 파일 삭제
	    	   fileService.delete(dbphotos.get(0).getFilePath().replace("\\", "/"));
	    	   photoRepository.deleteUserPhotoByFileId(dbphotos.get(0).getId());	
	    	   user.setProfileImage(null);
    	   }
       }else {
    	   if(dbphotos.size()==0) {
    		   System.out.println("ox");
    		   Photo photoList = fileHandler.parseFileInfo1(files);
		   		user.addPhoto(photoRepository.save(photoList));
		   		Long id = photoRepository.test(userNo);
		   		//PhotoDTO photoDto = photoService.findByFileId(id);
		           String absolutePath
		           	=allurl+"imagetest/"+id.toString();
		           //String path = photoDto.getFilePath();
		   		user.setProfileImage(absolutePath);
    	   }else {
    		   System.out.println("oo");
    		   if(dbphotos.get(0).getOrigFileName() == files.getOriginalFilename()) {
    			   
    		   }else {
    	    	   fileService.delete(dbphotos.get(0).getFilePath().replace("\\", "/"));
    	    	   //photoRepository.deleteUserPhotoByFileId(dbphotos.get(0).getId());	
    	    	   
    	    	   Photo photoList = fileHandler.parseFileInfo1(files);
	   		   		user.addPhoto(photoRepository.save(photoList));
	   		   		Long id = photoRepository.test(userNo);
	   		   		//PhotoDTO photoDto = photoService.findByFileId(id);
	   		           String absolutePath
	   		           	=allurl+"imagetest/"+id.toString();
	   		           //String path = photoDto.getFilePath();
	   		   		user.setProfileImage(absolutePath);
    	    	   
    		   }
    	   }
       }
    	   
        
        if (user != null) {
        	if(username!=null) {
        		user.setUsername(username);
        	}
        	if(gender!=null) {
        		user.setGender(gender);
        	}
        	if(birth!=null) {
        		user.setBirth(birth);
        	}
//        	if(photoList!=null) {
//        		user.addPhoto(photoRepository.save(photoList));
//        		Long id = photoRepository.test(userNo);
//        		//PhotoDTO photoDto = photoService.findByFileId(id);
//                String absolutePath
//                	=allurl+"imagetest/"+id.toString();
//                //String path = photoDto.getFilePath();
//        		user.setProfileImage(absolutePath);
//        	}
        	
        }
        return userrepository.save(user).getUserNo();
    }

    
	@Autowired 
	private UserCategoryRepository userCategoryRepository;
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
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
    private UserRepository userrepository;
	@Autowired
    private CategoryRepository categoryrepository;

    public void updateUser(int userNo, String username, Integer gender,Timestamp birth) {
        User user = userrepository.findByUserNo(userNo);//.orElse(null);
        if (user != null) {
        	if(username!=null) {
        		user.setUsername(username);
        	}
        	if(gender!=null) {
        		user.setGender(gender);
        	}
        	if(birth!=null) {
        		user.setBirth(birth);
        	}
//        	if(imageNo!=null) {
//        		user.setProfileImage(imageNo);
//        	}
        	
        }
    }
    
    public void modifyUserCategory(int userNo, ArrayList<String> categoryList) {
    	
    	List<UserCategory> category = userCategoryRepository.findByUserNo(userNo);
    	if(category!=null) {
    		userCategoryRepository.deleteAll(category);
    	}else {
    		throw new IllegalArgumentException("Data not found with value: " + userNo);
    	}
    	for(String categoryname : categoryList) {
    		Category categoryone = categoryrepository.findByName(categoryname);
    		
    		if(categoryone!=null) {
    			int categoryNo = categoryone.getCategoryNo();
    			UserCategory newcategory= new UserCategory();
	    		newcategory.setCategoryNo(categoryNo);
	    		newcategory.setUserNo(userNo);
	    		userCategoryRepository.save(newcategory);
    		}
    		
    	}
    }
    
    public boolean modifyUserPassword(int userNo, String password) {
    	
    	User user = userrepository.findByUserNo(userNo);
    	if(user!=null) {
    		
    		user.setPassword(password);
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public UserProfileDTO convertUserProfileDTO(List<Object[]> objectL) {
    	

//		for (Object element : object) {
//		    System.out.println(element);
//		}
    	Object[] object = objectL.get(0);
    	int userNo = (int) object[0];
    	String userProfileImage = (String) object[1];
    	String username = (String) object[2];
    	Timestamp birth = (Timestamp) object[3];
    	Integer gender = (Integer) object[4];
    	Integer followNum = (Integer) object[5];
    	Integer followeeNum = (Integer) object[6];
    	
    	UserProfileDTO userProfileDTO = new UserProfileDTO( userNo,
    			userProfileImage, username, birth,
                gender, followNum, followeeNum);
    	System.out.println(userProfileDTO);
    	return userProfileDTO;
    }
    
}
