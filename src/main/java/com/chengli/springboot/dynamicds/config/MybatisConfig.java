package com.chengli.springboot.dynamicds.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.chengli.springboot.dynamicds.dynmic.DynmicDataSource;
import com.github.pagehelper.PageHelper;

@Configuration
@MapperScan(basePackages = { "com.chengli.springboot.dynamicds" }, annotationClass = Mapper.class) // 定义扫描的ROOT包，以及注解
@EnableTransactionManagement // 开启注解事务
public class MybatisConfig {
	@Autowired
	private DruidConfigProperties druidConfigProperties;

	@Primary//设置为主要的，当同一个类型存在多个Bean的时候，spring 会默认注入以@Primary注解的bean
	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource springbootDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(druidConfigProperties.getDriverClassName());
		druidDataSource.setUrl(druidConfigProperties.getUrl());
		druidDataSource.setUsername(druidConfigProperties.getUsername());
		druidDataSource.setPassword(druidConfigProperties.getPassword());
		druidDataSource.setInitialSize(druidConfigProperties.getMinIdle());
		druidDataSource.setMinIdle(druidConfigProperties.getMinIdle());
		druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
		druidDataSource.setMaxWait(druidConfigProperties.getMaxWait());
		druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfigProperties.getTimeBetweenEvictionRunsMillis());
		druidDataSource.setMinEvictableIdleTimeMillis(druidConfigProperties.getMinEvictableIdleTimeMillis());
		druidDataSource.setValidationQuery(druidConfigProperties.getValidationQuery());
		druidDataSource.setTestWhileIdle(druidConfigProperties.getTestWhileIdle());
		druidDataSource.setTestOnBorrow(druidConfigProperties.getTestOnBorrow());
		druidDataSource.setTestOnReturn(druidConfigProperties.getTestOnReturn());
		druidDataSource.setPoolPreparedStatements(druidConfigProperties.getPoolPreparedStatements());
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfigProperties.getMaxPoolPreparedStatementPerConnectionSize());
		druidDataSource.setFilters(druidConfigProperties.getFilters());
		return druidDataSource;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource eziliaoDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(druidConfigProperties.getDriverClassName1());
		druidDataSource.setUrl(druidConfigProperties.getUrl1());
		druidDataSource.setUsername(druidConfigProperties.getUsername1());
		druidDataSource.setPassword(druidConfigProperties.getPassword1());
		druidDataSource.setInitialSize(druidConfigProperties.getMinIdle());
		druidDataSource.setMinIdle(druidConfigProperties.getMinIdle());
		druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
		druidDataSource.setMaxWait(druidConfigProperties.getMaxWait());
		druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfigProperties.getTimeBetweenEvictionRunsMillis());
		druidDataSource.setMinEvictableIdleTimeMillis(druidConfigProperties.getMinEvictableIdleTimeMillis());
		druidDataSource.setValidationQuery(druidConfigProperties.getValidationQuery());
		druidDataSource.setTestWhileIdle(druidConfigProperties.getTestWhileIdle());
		druidDataSource.setTestOnBorrow(druidConfigProperties.getTestOnBorrow());
		druidDataSource.setTestOnReturn(druidConfigProperties.getTestOnReturn());
		druidDataSource.setPoolPreparedStatements(druidConfigProperties.getPoolPreparedStatements());
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfigProperties.getMaxPoolPreparedStatementPerConnectionSize());
		druidDataSource.setFilters(druidConfigProperties.getFilters());
		return druidDataSource;
	}

	@Bean
	public DataSource dynmicDataSource() throws SQLException {
		DynmicDataSource dynmicDataSource = new DynmicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("springbootDataSource", springbootDataSource());
		targetDataSources.put("springbootDataSource1", eziliaoDataSource());
		dynmicDataSource.setTargetDataSources(targetDataSources);
		
		dynmicDataSource.setDefaultTargetDataSource(springbootDataSource());
		return dynmicDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynmicDataSource")DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));

		// 添加Mybatis插件，例如分页，在之类创建你插件添加进去即可，这里我就不做叙述了。
		// sqlSessionFactoryBean.setPlugins(new Interceptor[]{你的插件});
		//分页插件  
        PageHelper pageHelper = new PageHelper();  
        Properties props = new Properties();  
        props.setProperty("reasonable", "true");  
        props.setProperty("supportMethodsArguments", "true");  
        props.setProperty("returnPageInfo", "check");  
        props.setProperty("params", "count=countSql");  
        pageHelper.setProperties(props);  
        //添加插件  
		sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});  

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("dynmicDataSource")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}