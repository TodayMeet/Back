package com.cos.todaymeet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.todaymeet.Service.CustomUserDetailsService;
import com.cos.todaymeet.config.auth.oauth.PrincipleOAuth2UserService;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true ) // secure 어노테이션 활성화,preAuthorize활성화
public class SecurityConfig implements WebSecurityConfigurer{
	
	@Autowired
	private PrincipleOAuth2UserService principleOAuth2UserService;
	
//	@Bean
//	public BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	}
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
		int strength = 10; // 암호화 강도 설정
        return new BCryptPasswordEncoder(strength);
    }

//	@Override
//	public void init(SecurityBuilder builder) throws Exception {
//		// TODO Auto-generated method stub
////		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////		
//	}
//
//	@Override
//	public void configure(SecurityBuilder builder) throws Exception {
//		// TODO Auto-generated method stub
////		http.csrf().disable()
////            .authorizeRequests()
////            .antMatchers("/login").permitAll()
////            .anyRequest().authenticated()
////            .and()
////            .formLogin().permitAll();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.requestMatchers("/user/**").authenticated()
				.requestMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				.requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/login")//user,manager,admin페이지로 가면 login 페이지로 이동
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
				.and()
				.oauth2Login()
				.loginPage("/loginForm")//엑세스토큰 + 사용자프로필
				.userInfoEndpoint()
				.userService(principleOAuth2UserService)
				;
		return http.build();
		//구글 로그인 후 처리 1.코드받기-인증 2. 엑세스토큰-권한 3.사용자 프로필 가져오기
		// 4. 그 정보로 회원가입 자동 
	}

	@Override
	public void init(SecurityBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(SecurityBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
