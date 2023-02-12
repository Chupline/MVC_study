package kr.board.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/* 환결설정 */
@Configuration

/**
 * Mapper interface들을 스캔에서 메모리에 적재 <mybatis-spring:scan
 * base-package="kr.board.mapper"/>에 해당 */
@MapperScan(basePackages = { "kr.board.mapper" })

/**
 * main/resources에서 .properties가 있다면 가져온다. persistance-mysql.properties에
 * com.zaxxer.hikari.HikariConfig의 정보를 넣는다. */
@PropertySource({ "classpath:persistance-mysql.properties" })
public class RootConfig {

	/* properties를 참조하기위한 필요 클래스 */
	@Autowired
	private Environment env;

	/* HikariDataSource = HikariConfig 를 생성하고 properties의 값을 참조 \
	 * com.zaxxer.hikari.HikariDataSource에 해당 */
	@Bean
	public DataSource myDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
		hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
		hikariConfig.setUsername(env.getProperty("jdbc.user"));
		hikariConfig.setPassword(env.getProperty("jdbc.password"));
		HikariDataSource myDataSource = new HikariDataSource(hikariConfig);
		return myDataSource;
	}

	/* BoardMapper interface의 구현클래스 생성
	 * org.mybatis.spring.SqlSessionFactoryBean에 해당
	 * 위의 DataSource를 가져와서 Connection POOL 만들고
	 * mybatis에서 사용하기 위해 내부적으로 연결 */
	@Bean
	public SqlSessionFactory sessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(myDataSource());
		return (SqlSessionFactory) sessionFactory.getObject();
	}

}
