package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//자동으로 BEAN 등록이 된다 (@Repository 생략)
public interface UserRepository extends JpaRepository<User, Integer>{
	//select * from user WHERE username=?1;
	Optional<User> findByUsername(String username);
	
} 

//JPA Naming 쿼리
//select * from user WHERE username=?1 AND password=?2;
//?에 파라미터가 순서에 맞춰 들어간다.
//User findByUsernameAndPassword(String username, String password);

//	@Query(value="SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
//	User login(String username, String password);