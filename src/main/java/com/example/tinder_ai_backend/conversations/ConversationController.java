package com.example.tinder_ai_backend.conversations;

import com.example.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")  //http://localhost:8080/conversations
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request){
        profileRepository.findById(request.profileId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));   //this checks if the id of profile exists,if profile doesnot exists it throws exception
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        return conversation;
    }

    public record CreateConversationRequest(    //its a record made for the id of ai that we r conversing with
            String profileId
    ){}

}
