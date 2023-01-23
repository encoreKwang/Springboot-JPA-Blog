package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {// username, password, email
		System.out.println("UserApiController save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		//자바오브젝트를 Jackson라이브러리가 JSON데이터로 변환해서 리턴함.
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail){
		userService.회원수정(user, principalDetail);
		//여기서는 트랜잭션이 종료되기 대문에 DB에 값은 변경이 됐음.
		//하지만 세션값은 변경되지 않은 상태이기 떄문에 우리가 직접 세션값을 변경해줄 것임.
		//-> 해결책1: 컨트롤러단에서 파라미터로 principalDetail 받고 service단에 넘겨줌
		//서비스 단 마지막에 principalDetail.setUser(psersistenceUser);
		
		//-> 해결책2: 직접 세션 등록(기존 세션에 덮어쓰기)
		//DB에 이미 변경 후 인코딩된 PW가 들어있기 때문에 user.PW도 변경된 해당 값이 들어있음.  
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	//전통적인 로그인 방식
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session){
	 * System.out.println("UserApiController login 호출됨"); User principal =
	 * userService.로그인(user); //pricipal (접근 주체) if(principal != null) {
	 * session.setAttribute("principal", principal); }
	 * 
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
	 */
}
