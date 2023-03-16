package com.nextuple.orderpricingapp;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class, proxyBeanMethods = false)
@EntityScan("com.nextuple.orderpricingapp.entities")
@EnableJpaRepositories("com.nextuple.orderpricingapp.repositories")
@ComponentScan("com.nextuple.orderpricingapp.*")
//@ImportRuntimeHints(NativeImageRuntimeHintsConfiguration.HibernateRegistrar.class)
//@TypeHint(types = PostgreSQL95Dialect.class, typeNames = "org.hibernate.dialect.PostgreSQLDialect")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
//
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/**")
//						.allowedOrigins("http://localhost:3000")
//						.allowedMethods("GET", "POST","PATCH")
//						.allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "content-type")
//						.exposedHeaders("header1", "header2")
//						.allowCredentials(true).maxAge(3600);
//			}
//		};
//	}
//
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("postgresql");
		dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgres");
		dataSourceBuilder.username("postgres");
		dataSourceBuilder.password("postgrespw");
		return dataSourceBuilder.build();
	}
//
//


}
