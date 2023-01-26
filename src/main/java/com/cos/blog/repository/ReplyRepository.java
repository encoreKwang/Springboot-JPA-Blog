package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	//replySaveRequestDto가 가지고 있는 필드 값 3개 [userId, boardId,content]가 "순서대로" 자동으로 들어간다.
	//User 객체와 Board객체를 영속화해서 넣을 필요가 없어짐
//	@Modifying
//	@Query(value ="INSERT INTO reply(content, boardId, userId, createDate) VALUES (:content, :boardId, :userId, now())", nativeQuery = true)
//	@Modifying
//	@Query(value="INSERT INTO reply(content, boardId, userId, createDate) VALUES(:content, :boardId, :userId, now())", nativeQuery = true)
    @Modifying
    @Query(value="INSERT INTO reply(content, boardId, userId, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(String content , int boardId ,int userId );//jdbc가 기본적으로 업데이트된 행의 개수를 리턴함.
	//실패시 -1 리턴
	
}
