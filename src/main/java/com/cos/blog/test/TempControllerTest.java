package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("temp");
		//파일리턴 기본경로: src/main/resources/static
		//리턴명: /home.html
		return "/home.html"; 
	}
	
		
	@GetMapping("/temp/jsp")
	public String tempHomeJsp() {
		//  prefix
		//suffix
		//풀 경로: /WEB-INF/views/test.jsp
		return "test"; 
	}
	
	
}
