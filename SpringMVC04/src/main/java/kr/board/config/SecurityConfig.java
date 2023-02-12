package kr.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/* WebSecurityConfigurerAdapter클래스를 상속하여 SecurityConfig객체를 생성한다. */

@Configuration /* 환결설정 */
@EnableWebSecurity /* 스프링MVC와 스프링 시큐리티를 결합하는 클래스이다. */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 요청에 대한 보안 설정
		// 한글 인코딩같은 경우 필터를 걸쳐 보안설정을 하게된다.
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter,CsrfFilter.class); 

	}
	
	
	
}
