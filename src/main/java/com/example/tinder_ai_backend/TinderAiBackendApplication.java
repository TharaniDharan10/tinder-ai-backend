package com.example.tinder_ai_backend;

import com.example.tinder_ai_backend.conversations.ConversationRepository;
import com.example.tinder_ai_backend.matches.MatchRepository;
import com.example.tinder_ai_backend.profiles.ProfileCreationService;
import com.example.tinder_ai_backend.profiles.ProfileRepository;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner{

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private MatchRepository matchRepository;



	@Autowired
	private ProfileCreationService profileCreationService;

	@Autowired
	private ChatClient chatClient;


	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) {	//this is method implemented by CommandLineRunner which runs when the app is up

		clearAllData();
		profileCreationService.createProfiles(0);
		profileCreationService.saveProfilesToDB();

	}

	private void clearAllData() {
		conversationRepository.deleteAll();
		matchRepository.deleteAll();;
		profileRepository.deleteAll();
	}
}

