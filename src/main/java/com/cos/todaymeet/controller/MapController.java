package com.cos.todaymeet.controller;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cos.todaymeet.DTO.LatLon;
import com.cos.todaymeet.DTO.MapDTO;
import com.cos.todaymeet.DTO.MapPinDto;
import com.cos.todaymeet.Service.MapService;
import com.cos.todaymeet.repository.MeetRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController {
	
	private final String url = "https://dapi.kakao.com/v2/local/search/address";
	@Value("${kakao-key}")
	private String key; 
	@Autowired
	private MapService mapService;
	@Autowired
	private MeetRepository meetRepository;	
	/*****************************************************************************/
	//API 주소검색
	// postman - Others/주소검색
	// 동의 이름을 입력하면 해당 동과 같은 이름을 가진 행정구역?리스트를 반환한다.
	// 동의 이름과 위도, 경도가 출력된다.
	// 메인지도 - 동 검색 페이지
	@GetMapping("/address")
	public ArrayList findLocation(@RequestParam String query){
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + key); //Authorization 설정
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders); //엔티티로 만들기
        URI targetUrl = UriComponentsBuilder
                .fromUriString(url) //기본 url
                .queryParam("query", query) //인자
                .build()
                .encode(StandardCharsets.UTF_8) //인코딩
                .toUri();

        //GetForObject는 헤더를 정의할 수 없음
        ResponseEntity<Map> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class);
        LinkedHashMap meta = (LinkedHashMap) result.getBody().get("meta");
        if(meta.get("total_count").toString()=="0") {
        	return null;
        }
        //System.out.println(result.getBody().get("documents").toString());
        //System.out.println(result.getBody().get("documents").getClass());
        ArrayList<LinkedHashMap> Locations = (ArrayList) result.getBody().get("documents");
        ArrayList mapList = new ArrayList();
        for(LinkedHashMap location : Locations) {
        	//System.out.println(location.getClass());
        	//System.out.println(location.get("address_name"));
        	MapDTO mapdata = new MapDTO();
        	mapdata.setAddress_name(location.get("address_name").toString());
	        //System.out.println(mapdata.getAddress_name());
	        mapdata.setLat(location.get("y").toString());
	        //System.out.println(mapdata.getLat());
	        mapdata.setLon(location.get("x").toString());
	        //System.out.println(mapdata.getLon());
	        mapList.add(mapdata);
        }
        //System.out.println(mapList);
        return mapList; //내용 반환
		
	}
	/***********************************************************************************************/
	//API 모임 핀 리스트 출력
	//postman meet/모임 지도 핀 출력
	//사용자로부터 좌표를 받아서 해당 좌표를 포함하는 동을 검색 후 동 이름을 이용하여 모임 정보를 가져온다.
	@PostMapping("/meet/pinlist")
	public  List<MapPinDto> pinList(
			@RequestBody LatLon latlon){
		//System.out.println("pinStart");
		//System.out.println(latlon);
		//좌표를 이용한 동 검색
		String address = mapService.getDataFromExternalAPI((String)latlon.getLat(),latlon.getLon());
		//해당 동에 있는 모임 검색
		List<Object[]> pinlist = meetRepository.selectPinByAddress(address);
		List<MapPinDto> showpinlist = new ArrayList<>();
		//리스트로 변환
		for(Object[] pin : pinlist) {
			MapPinDto p = new MapPinDto((int)pin[0],(String)pin[1],(String)pin[2],(String)pin[3],
					(Timestamp)pin[4]);
			showpinlist.add(p);
		}
		//System.out.println("pinEnd");
		return showpinlist;
		
	}

}
