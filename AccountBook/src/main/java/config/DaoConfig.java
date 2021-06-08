package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dao.AccountBookDao;
import dao.AccountDao;
import dao.AuthDao;
import dao.ConversionAccountDao;
@Configuration
@EnableTransactionManagement
public class DaoConfig {
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("org.mariadb.jdbc.Driver");
		ds.setUrl("jdbc:mariadb://localhost/accountbook?characterEncoding=utf8mb4_general_ci");
		ds.setUsername("");
		ds.setPassword("");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		return ds;
	}
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	@Bean
	public AccountDao accountDao() {
		return new AccountDao(dataSource());
	}
	
	@Bean 
	public AccountBookDao accountBookDao() {
		return new AccountBookDao(dataSource());
	}
	
	@Bean ConversionAccountDao conversionAccountDao() {
		return new ConversionAccountDao(dataSource());
	}
	
	@Bean AuthDao authDao() {
		return new AuthDao(dataSource());
	}
}
