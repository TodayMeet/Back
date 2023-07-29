package com.cos.todaymeet.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.todaymeet.DTO.NoticeCreateDTO;
import com.cos.todaymeet.model.Notice;
import com.cos.todaymeet.model.NoticePhoto;
import com.cos.todaymeet.repository.NoticeFileHandler;
import com.cos.todaymeet.repository.NoticePhotoRepository;
import com.cos.todaymeet.repository.NoticeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class NoticeService {
	@Autowired 
	private NoticeRepository noticeRepository;
	@Autowired
	private NoticeFileHandler NoticefileHandler;
	@Autowired
	private NoticePhotoRepository NoticephotoRepository;
	@Value("${url}")
	private String allurl;
	
	@Transactional
    public int create(
    		NoticeCreateDTO requestDto,
        List<MultipartFile> files
    ) throws Exception {
		Notice notice = new Notice(
				null,requestDto.getTitle(),
				requestDto.getContent(),
				null,
				false);
		 List<NoticePhoto> photoList = NoticefileHandler.parseFileInfo(files);
		 
		  	// 파일이 존재할 때에만 처리
		        if(!photoList.isEmpty()) {
		        	NoticePhoto p = photoList.get(0);
		            for(NoticePhoto photo : photoList) {
		                // 파일을 DB에 저장
		            	NoticePhoto np = NoticephotoRepository.save(photo);
		            	if(notice.getImage()==null) {
		            		notice.setImage(allurl+"notice/image/"+np.getId());
		            	}
		  		        notice.addPhoto(np);
		  		        
		            }
		        }
		        
		        
		        
		return noticeRepository.save(notice).getNoticeNo();
	}
	
}
