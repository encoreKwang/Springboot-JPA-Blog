package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //빈등록 (IoC 관리)
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 "미리" 체크하겠다는 뜻.
//즉, 요청을 실행하고 나서 권한 및 인증을 수행하는게 아니라 요청을 실행 하기전 미리 체크하겠다
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		  .csrf().disable() //csrf 토큰 비활성화(테스트시 걸어두는게 좋음
		  .authorizeHttpRequests()
		    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		  .and()
		    .formLogin()
		    .loginPage("/auth/loginForm");
		
		return http.build();
	}
}
