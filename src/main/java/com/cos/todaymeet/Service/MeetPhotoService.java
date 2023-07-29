package com.cos.todaymeet.Service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.todaymeet.DTO.MeetPhotoResponseDto;
import com.cos.todaymeet.DTO.PhotoDTO;
import com.cos.todaymeet.DTO.PhotoResponseDto;
import com.cos.todaymeet.model.MeetPhoto;
import com.cos.todaymeet.model.Photo;
import com.cos.todaymeet.repository.MeetPhotoRepository;
import com.cos.todaymeet.repository.PhotoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MeetPhotoService {
	private final PhotoRepository photoRepository;
	private final MeetPhotoRepository meetphotoRepository;
    /**
     * 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public PhotoDTO findByFileId(Long id){

        Photo entity = photoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        PhotoDTO photoDto = PhotoDTO.builder()
                .origFileName(entity.getOrigFileName())
                .filePath(entity.getFilePath())
                .fileSize(entity.getFileSize())
                .build();

        return photoDto;
    }

    /**
     * 이미지 전체 조회
     */
    @Transactional(readOnly = true)
    public List<MeetPhotoResponseDto> findAllByMeet(int meetNo){

        List<MeetPhoto> photoList = meetphotoRepository.findAllByMeet(meetNo);

        return photoList.stream()
                .map(MeetPhotoResponseDto::new)
                .collect(Collectors.toList());
    }
}
