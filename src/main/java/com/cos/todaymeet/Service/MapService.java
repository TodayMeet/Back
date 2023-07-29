package com.cos.todaymeet.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MapService {
	 private final RestTemplate restTemplate;
	 private final ObjectMapper objectMapper;
	 @Autowired
	 public MapService(RestTemplate restTemplate, ObjectMapper objectMapper) {
	        this.restTemplate = restTemplate;
	        this.objectMapper = objectMapper;
	    }

	    public String  getDataFromExternalAPI(String lat,String lon) {
	        String url = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x="+lon+"&y="+lat;
	        // Authorization 값 설정
	        String token = "KakaoAK key값을 넣여주세요";
	        HttpHeaders headers = new HttpHeaders();
	        headers.set(HttpHeaders.AUTHORIZATION, token);
	        
	        // 요청에 헤더 설정
	        HttpEntity<String> entity = new HttpEntity<>(headers);

	        // 요청 보내기
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        String responseBody = response.getBody();
	        System.out.println(responseBody);
	        String region3DepthName = "";
	        try {
	            // JSON 데이터 파싱
	            JsonNode jsonNode = objectMapper.readTree(responseBody);
	            System.out.println(jsonNode);
	            JsonNode documentsNode = jsonNode.get("documents");
	            if (documentsNode.isArray()) {
	                for (JsonNode documentNode : documentsNode) {
	                    String regionType = documentNode.get("region_type").asText();
	                    if (regionType.equals("B")) {
	                        region3DepthName = documentNode.get("region_3depth_name").asText();
	                        System.out.println("region_3depth_name: " + region3DepthName);
	                    }
	                }
	            }
	            return region3DepthName;
	        } catch (Exception e) {
	            // 예외 처리
	        	return null;
	        }
	        
	    }
}
