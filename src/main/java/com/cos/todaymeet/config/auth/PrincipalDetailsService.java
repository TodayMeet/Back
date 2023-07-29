package com.cos.todaymeet.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.UserRepository;

//시큐리티설정에서 loginprocessingurl login이 되어있어서
//login요청이 오면 자동으로 userdetailuservice타입으로 되어잇는 loadbyusername 작성
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//시큐리티 session = authentication =userdetails
	//session내부에 authentication ,내부에 userdetails
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username" +username);
		User user = userRepository.findByUsername(username);
		if(user == null) {
			System.out.println("false");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}else {
			return new PrincipalDetails(user);
		}
		
	}

}
