package com.m2m.trello.controller;

import com.m2m.trello.dto.MemberDto;
import com.m2m.trello.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

	private final PasswordEncoder passwordEncoder;
	private final LoginService loginService;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@PostMapping("/login")
	public HashMap<String,String> login(@RequestBody MemberDto memberDto) throws Exception {
		
		HashMap<String,String> resultMap = new HashMap<>();
		
		if(!(memberDto.getUsername() == null || memberDto.getPassword() == null ||
				memberDto.getUsername().equals("") || memberDto.getPassword().equals(""))) {
			
			try {
				String token = loginService.login(memberDto);
				
				resultMap.put("token", token);
				resultMap.put("result_cd", "S");
			} catch(IllegalArgumentException e) {
				resultMap.put("result_cd", "F");
			}
			
		} else {
			resultMap.put("result_cd", "F");
		}
		return resultMap;
	}
	
	@PostMapping("/member")
    public int join(@RequestBody MemberDto memberDto) {
        return loginService.join(memberDto);
    }
	
}
