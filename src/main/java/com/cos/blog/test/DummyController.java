package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {
	
	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e){
			return "삭제에 실패함. 해당 id는 db에 없음.";
		}
		return "삭제되었습니다. id : " + id;
	}

	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	//email, password
	@Transactional //함수 종료시에 자동 업데이트됨.
	@PutMapping("/dummy/user/{id}") //json형식으로 요청이 들어옴 => @RequestBody
	public User updateUser(@PathVariable int id , @RequestBody User requestUser) {
		//웹브라우저가 json데이터로 요청을 했는데, 그것을 스프링의 MessageConverter의 Jackson라이브러리가
		//java object로 반환해서 받아준다
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		}); 
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		userRepository.save(user);
		//더티 체킹 : 변경을 감지해서 db에 수정 update를 실행함 (update할 때, 더티 체킹 방식을 사용)
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	//한페이지 당 2건의 데이터를 리턴 받음
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUsers = userRepository.findAll(pageable);
		List<User> users = pagingUsers.getContent();
		return users;
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				return new User();
//			}
//		});
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다.id: " + id);
			}			
		});
		//웹브라우저가 요청.
		//응답하는 user 객체는 자바 오브젝트.
		//응답할 때, 웹브라우저가 이해할 수 있는 데이터로 변환 필요 ->이전에는 json (Gson 라이브러리) 사용
		//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던저줍니다.
		return user;
	}
	

	@PostMapping("/dummy/join")
	public String join(User user) {	//form태그 형식으로 데이터를 보냄
//		System.out.println("username : " + username);
//		System.out.println("password :" + password);
//		System.out.println("email : " + email);
		
		user.setRole(RoleType.USER);

		userRepository.save(user);
		return "가입 완료";
	}
	
}
