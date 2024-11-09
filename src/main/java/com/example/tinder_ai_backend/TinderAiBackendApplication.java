package com.example.tinder_ai_backend;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TinderAiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

}

