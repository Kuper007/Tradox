package com.nc.tradox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

@SpringBootApplication
@EnableSwagger2
public class TradoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradoxApplication.class, args);
	}

	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
		dataSourceBuilder.url("jdbc:oracle:thin:@91.219.60.189:1521/XEPDB1");
		dataSourceBuilder.username("kuperman_anton");
		dataSourceBuilder.password("Oracle11XE#");
		return dataSourceBuilder.build();
	}
}