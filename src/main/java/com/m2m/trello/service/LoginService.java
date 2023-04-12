package com.m2m.trello.service;

import java.util.Collections;

import com.m2m.trello.dto.MemberDto;
import com.m2m.trello.mapper.LoginMapper;
import com.m2m.trello.token.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final LoginMapper loginMapper;
	private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
	
    
    public int join(MemberDto memberDto){
        
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setRoles(Collections.singletonList("ROLE_USER"));
        loginMapper.save(memberDto);
        return memberDto.getMemberId();
    }
    
	public String login(MemberDto memberDto) throws Exception {
		
		MemberDto findMember = loginMapper.findByEmail(memberDto.getEmail());
		
		if(findMember == null) {
			throw new IllegalArgumentException("가입되지 않은 E-MAIL 입니다.");
		} else if(!passwordEncoder.matches(memberDto.getPassword(), findMember.getPassword())) { 
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		} else {
			return jwtTokenProvider.createToken(findMember.getEmail(), findMember.getRoles());
		}
	}
	
}
