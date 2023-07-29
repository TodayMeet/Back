package com.cos.todaymeet.Service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
@Service
public class fileService {
	public void delete(String filePath) {
		 String srcFileName = "images/meet/20230628/1125425762989900.jpg";
		 try {
			    FileUtils.forceDelete(new File(filePath));
			    // 파일 삭제 성공
			} catch (IOException e) {
			    // 파일 삭제 실패
			    e.printStackTrace();
			}

	}
	
}
