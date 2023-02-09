package com.hyj.sta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.hyj")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.hyj.sta.mapper")
@EnableScheduling
@EnableTransactionManagement
public class StaApplication {
	public static void main(String[] args) {
		SpringApplication.run(StaApplication.class, args);
	}
}
