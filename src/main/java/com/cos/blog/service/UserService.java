package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional //하나의 트랜잭션으로 묶여서 모두 성공시 커밋 or 하나라도 실패시 rollback
	public void 회원가입(User user) {
		userRepository.save(user);
		
//		try {
//			userRepository.save(user);
//			return 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("UserService: 회원가입() : " + e.getMessage());
//		}
//		return -1;
	}
	
}
