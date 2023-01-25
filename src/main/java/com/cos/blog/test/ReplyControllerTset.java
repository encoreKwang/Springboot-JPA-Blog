package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTset {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get();
		//무한참조 테스트 : jackson 라이브러리(오브젝트를 json으로 리턴) => 모델의 getter를 호출
	}

	@GetMapping("/test/reply")
	public List<Reply> getReply() {
		return replyRepository.findAll();
		//무한참조 테스트 : jackson 라이브러리(오브젝트를 json으로 리턴) => 모델의 getter를 호출
		//다이렉트로 reply에 접근하면 데이터를 전부 주지만
		//Model board를 통해 다시 reply에 접근하게 되면 ignore해둔 필드는 더 이상 가져오지 않는다. 
	}
}
