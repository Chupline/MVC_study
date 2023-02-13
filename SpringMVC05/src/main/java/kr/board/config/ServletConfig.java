package kr.board.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/* 환결설정 */
@Configuration

/* 현재 클래스를 Web MVC로 사용
 <annotation-driven />에 해당 : annotation을 활성화*/
@EnableWebMvc  

/* <context:component-scan base-package="kr.board.controller"/>
    : 패키지내의 컨트롤러를 읽어서 POJO를 자동으로 메모리에 적재		*/
@ComponentScan(basePackages = {"kr.board.controller"})

//servlet-context.xml을 대신하는 클래스
public class ServletConfig implements  WebMvcConfigurer{
	/**
	 * servlet-context.xml에서 resources 설정
	 * <resources mapping="/resources/**" location="/resources/" /> */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	/**
	 * servlet-context.xml에서 뷰 리졸버 설정
	 * org.springframework.web.servlet.view.InternalResourceViewResolver */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
	InternalResourceViewResolver bean = new InternalResourceViewResolver();
	bean.setPrefix("/WEB-INF/views/");	// prefix설정
	bean.setSuffix(".jsp");				// suffix설정
	registry.viewResolver(bean); 
	}
	

}
