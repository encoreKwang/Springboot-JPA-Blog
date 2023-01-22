package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

@Configuration //빈등록 (IoC 관리)
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 "미리" 체크하겠다는 뜻.
//즉, 요청을 실행하고 나서 권한 및 인증을 수행하는게 아니라 요청을 실행 하기전 미리 체크하겠다
public class SecurityConfig {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	//입력한 password(raw)를 인코딩 처리해서 DB해쉬값과 알아서 비교해줌.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		  .csrf().disable() //csrf 토큰 비활성화(테스트시 걸어두는게 좋음, csrf 토큰 없이 요청을 하면 시큐리티가 막기 때문에)
		  .authorizeHttpRequests()
		    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		  .and()
		    .formLogin()
		    .loginPage("/auth/loginForm")//인증이 필요한 어떤 페이지에 대한 요청은 loginForm으로 온다
		    .loginProcessingUrl("/auth/loginProc")
		    .defaultSuccessUrl("/");//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
		
		
		return http.build();
	}
}
