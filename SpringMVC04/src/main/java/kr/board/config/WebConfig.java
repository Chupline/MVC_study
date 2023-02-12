package kr.board.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web.xml 대신하는 클래스 => WebConfig.java
 * AbstractAnnotationConfigDispatcherServletInitializer
 *  : 스프링에서 가장 처음에 실행되는 클래스파일을 상속 받아서 생성한다. */
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * web.xml에서의 DB연동, 커넥션 풀 생성 
	 * <context-param> contextConfigLocation
	 * <listener> org.springframework.web.context.ContextLoaderListener
	 * => root-context.xml를 대신할 클래스 RootConfig.java 생성 해야한다. */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { RootConfig.class, SecurityConfig.class }; // DB,보안설정을 메모리에 올리기 위함
	}

	/**
	 * web.xml에서의 servlet설정
	 * (FrontController 생성 -> 컨테이너 호출)
	 * => servlet-context.xml를 대신할 클래스 ServletConfig.java 생성 해야한다.
	 * servlet> appServlet에 해당 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { ServletConfig.class };
	}

	/**
	 * web.xml에서의 servlet-mapping
	 * <servlet-mapping> appServlet에 해당 */
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	// web.xml에서의 한글 인코딩 설정
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] { encodingFilter };
	}
	

}
