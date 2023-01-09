package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {
	
	@GetMapping("/http/lombok")
	public String lombokT() {
//		Member m = new Member();
//		Member.class
		Member m = Member.builder().username("ss").password("1234").build();
		System.out.println(m.getUsername());
		m.setEmail("refad");
		return "lombok test";
	}
	
	@GetMapping("/http/get")
	public String getString(Member m) {
		return "get요청 " + m.getId() + " " + m.getUsername()
		+" " + m.getPassword();
	}
	
	@PostMapping("/http/post")
	public String postString(Member m) {
		return "post요청 " + m.getId() + " " + m.getUsername()
		+" " + m.getPassword();
	}
	@PutMapping("/http/put")
	public String putString() {
		return "put요청";
	}
	@DeleteMapping("/http/delete")
	public String deleteString() {
		return "delete요청";
	}
}
