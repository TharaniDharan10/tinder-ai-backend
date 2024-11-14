package com.example.tinder_ai_backend.conversations;

import com.example.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find profile with the ID : "+request.profileId()));  //this checks if the id of profile exists,if profile doesnot exists it throws exception
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        return conversation;
    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId , @RequestBody ChatMessage chatMessage){
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find conversation with the ID : "+conversationId));

        profileRepository.findById(chatMessage.authorId())
                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find a profile with ID : "+chatMessage.authorId()));

        //TODO
        ChatMessage messageWithTime = new ChatMessage(chatMessage.messageText(), chatMessage.authorId(),LocalDateTime.now());//here we copy the chatmessage to change the time according to recievers timezone
        conversation.messages().add(messageWithTime);
        conversationRepository.save(conversation);
        return conversation;
    }


    public record CreateConversationRequest(
            String profileId
    ) {
    }

}
