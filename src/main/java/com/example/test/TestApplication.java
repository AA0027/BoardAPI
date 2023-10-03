package com.example.test;

import com.example.test.dao.Member;
import com.example.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class TestApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
		}
		@Bean
		public RestTemplate restTemplate() {return new RestTemplate();}
}
