package com.cos.todaymeet.config.auth.oauth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.todaymeet.config.auth.PrincipalDetails;
import com.cos.todaymeet.config.auth.oauth.provider.GoogleUserInfo;
import com.cos.todaymeet.config.auth.oauth.provider.OAuth2UserInfo;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.UserRepository;
@Service
public class PrincipleOAuth2UserService extends DefaultOAuth2UserService{
	@Autowired
	private UserRepository userRepository;
//
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회
		System.out.println(userRequest.getClientRegistration());//서버정보 registrationId -어떤 OAuth로 로그인
		System.out.println(userRequest.getAccessToken().getTokenValue());
		System.out.println(super.loadUser(userRequest).getAttributes());
//		// code를 통해 구성한 정보
//		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
//		// token을 통해 응답받은 회원정보
//		System.out.println("oAuth2User : " + oAuth2User);
		String provider = userRequest.getClientRegistration().getClientId();
		String providerId=oAuth2User.getAttribute("sub");
		String username = provider+"_"+providerId;
		String password = "1234";
		String role = "ROLE_USER";
		String email =oAuth2User.getAttribute("email");
		
		User userEntity=userRepository.findByUsername(username);
		if(userEntity==null) {
			userEntity=User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("이미 로그인 했어");
		}
		
//		return processOAuth2User(userRequest, oAuth2User);	
		return new PrincipalDetails(userEntity,oAuth2User.getAttributes());


	}
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청~~");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} 
//			else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
//			System.out.println("페이스북 로그인 요청~~");
//			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
//		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
//			System.out.println("네이버 로그인 요청~~");
//			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
//		} else {
//			System.out.println("우리는 구글과 페이스북만 지원해요 ㅎㅎ");
//		}

		//System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
		//System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
		Optional<User> userOptional = 
				userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			// user가 존재하면 update 해주기
			user.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
		} else {
			// user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
			user = User.builder()
					.username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		}
		return null;
		//return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}
