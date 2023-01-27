package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional //하나의 트랜잭션으로 묶여서 모두 성공시 커밋 or 하나라도 실패시 rollback
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				});//영속화 완료(영속성 컨텍스트로 db데이터를 가져옴)
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시 (service가 종료될 때)트랜잭션이 종료됩니다. 이 때 더티체킹 - 자동 업데이트가 됨. DB flush(커밋)
	}
	
//	@Transactional
//	public void 댓글쓰기(User user, int boardid, Reply requestReply) {
//		Board board = boardRepository.findById(boardid)
//				.orElseThrow(()->{
//					return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다."); 
//				});//영속화 완료
//		requestReply.setUser(user);
//		requestReply.setBoard(board);
//		
//		replyRepository.save(requestReply);
//	}
//	@Transactional
//	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
//		User user = userRepository.findById(replySaveRequestDto.getUserId())
//				.orElseThrow(()->{
//					return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다."); 
//				});
//		Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
//				.orElseThrow(()->{
//					return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다."); 
//				});//영속화 완료
//		
//		Reply reply = Reply.builder()
//				.user(user)
//				.board(board)
//				.content(replySaveRequestDto.getContent())
//				.build();
////		Reply reply = new Reply();
////		reply.update(user, board, replySaveRequestDto.getContent());
//
//		
//		replyRepository.save(reply);
//	}
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {		
		int result = replyRepository.mSave(replySaveRequestDto.getContent(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getUserId());
		System.out.println("댓글쓰기 : " + result);
	}
	@Transactional
	public void 댓글삭제(int replyId) {		
		replyRepository.deleteById(replyId);
	}
}
