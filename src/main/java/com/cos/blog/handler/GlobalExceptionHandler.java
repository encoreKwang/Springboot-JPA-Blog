package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //어디서든 이벤트 발생하면 해당 클래스로 들어옴
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public String HandlerArgumentException(Exception e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
}
