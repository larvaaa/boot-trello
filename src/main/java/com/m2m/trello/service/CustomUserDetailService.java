package com.m2m.trello.service;

import com.m2m.trello.dto.MemberDto;
import com.m2m.trello.mapper.LoginMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    	
    	MemberDto findMember = loginMapper.findByEmail(loginId);
    	
    	if(findMember == null) {
    		throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    	} else {
    		return findMember;
    	}
    }
    
}
