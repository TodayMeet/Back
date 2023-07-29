package com.cos.todaymeet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.todaymeet.DTO.PhotoDTO;
import com.cos.todaymeet.Service.MeetPhotoService;
import com.cos.todaymeet.Service.PhotoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PhotoController {
	private final PhotoService photoService;
	private final MeetPhotoService meetphotoService;
	
    /**
     * 썸네일용 이미지 조회
     */
    @CrossOrigin
    @GetMapping(
            value = "/thumbnailImage/{id}",
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
            value = "/imagetest/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        PhotoDTO photoDto = photoService.findByFileId(id);
        String absolutePath
        	= new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();
        System.out.println("path" + path);
        String imagepath;
        boolean fileExists = checkFileExists(absolutePath + path);
        System.out.println(fileExists);
        if(fileExists) {
        	imagepath = absolutePath + path;
        }else {
        	System.out.println("not exists");
        	imagepath = absolutePath + "images/defaultProfile.jpg";
        }
        InputStream imageStream = new FileInputStream(imagepath);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
	        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
    
        public boolean checkFileExists(String filePath) {
            File file = new File(filePath);
            return file.exists();
        }
    
    @CrossOrigin
    @GetMapping(
            value = "/meetimage/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getmeetImage(@PathVariable Long id) throws IOException {
        PhotoDTO photoDto = photoService.findByMeetFileId(id);
        System.out.println(photoDto.getFilePath());
        String absolutePath
        	= new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();
        System.out.println(path);
        boolean check = checkFileExists(absolutePath+path);
        System.out.print(check);
        if(check) {
        	InputStream imageStream = new FileInputStream(absolutePath + path);
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
        else {
        	InputStream imageStream = new FileInputStream(absolutePath + "images/image_not_found.jpg");
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
        
    }
    //광고이미지
    @CrossOrigin
    @GetMapping(
            value = "/ad/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getAdImage(@PathVariable Long id) throws IOException {
        PhotoDTO photoDto = photoService.findByAdFileId(id);
        System.out.println(photoDto.getFilePath());
        String absolutePath
        	= new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();
        System.out.println(path);
        boolean check = checkFileExists(absolutePath+path);
        System.out.print(check);
        if(check) {
        	InputStream imageStream = new FileInputStream(absolutePath + path);
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
        else {
        	InputStream imageStream = new FileInputStream(absolutePath + "images/image_not_found.jpg");
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
        
    }
    @CrossOrigin
    @GetMapping(
            value = "/notice/image/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getNoticeImage(@PathVariable Long id) throws IOException {
        PhotoDTO photoDto = photoService.findByNoticeFileId(id);
        String absolutePath
        	= new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();
        boolean check = checkFileExists(absolutePath+path);
        System.out.print(check);
        if(check) {
        	InputStream imageStream = new FileInputStream(absolutePath + path);
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
        else {
        	InputStream imageStream = new FileInputStream(absolutePath + "images/image_not_found.jpg");
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
        
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        }
    }
}
