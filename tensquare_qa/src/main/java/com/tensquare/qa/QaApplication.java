package com.tensquare.qa;
import com.tensquare.qa.client.LabelClient;
import entity.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import util.IdWorker;
import util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient

@EnableDiscoveryClient
@EnableFeignClients
public class QaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil(){return new JwtUtil();}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	
}
