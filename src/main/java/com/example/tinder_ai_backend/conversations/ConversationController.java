package com.example.tinder_ai_backend.conversations;

import com.example.tinder_ai_backend.profiles.Profile;
import com.example.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
//
//@RestController
//public class ConversationController {
//
//    private final ConversationRepository conversationRepository;
//
//
//    private final ProfileRepository profileRepository;
//
//    private final ConversationService conversationService;
//
//
//    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
//        this.conversationRepository = conversationRepository;
//        this.profileRepository = profileRepository;
//        this.conversationService = conversationService;
//    }
//
//    @PostMapping("/conversations")  //http://localhost:8080/conversations
//    public Conversation createNewConversation(@RequestBody CreateConversationRequest request){
//        profileRepository.findById(request.profileId())
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find profile with the ID : "+request.profileId()));  //this checks if the id of profile exists,if profile doesnot exists it throws exception
//        Conversation conversation = new Conversation(
//                UUID.randomUUID().toString(),
//                request.profileId(),
//                new ArrayList<>()
//        );
//        conversationRepository.save(conversation);
//        return conversation;
//    }
//
//    @CrossOrigin(origins = "*") //this means from whichever server we call this api from,u can send response for this api.we do this since we fetch these from react which runs on other server.If i dont do this,when i try to access this api,there will be cors issue
//    @GetMapping("/conversations/{conversationId}")
//    public Conversation getConversation(@PathVariable String conversationId){
//        return conversationRepository.findById(conversationId)
//                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find conversation with the ID : "+conversationId));
//
//    }
//
//    @CrossOrigin(origins = "*") //this means from whichever server we call this api from,u can send response for this api.we do this since we fetch these from react which runs on other server
//    @PostMapping("/conversations/{conversationId}")
//    public Conversation addMessageToConversation(@PathVariable String conversationId , @RequestBody ChatMessage chatMessage){
//        Conversation conversation = conversationRepository.findById(conversationId)
//                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find conversation with the ID : "+conversationId));
//
//        String matchProfileId = conversation.profileId();
//
//        Profile profile = profileRepository.findById(matchProfileId)
//                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find a profile with ID : "+matchProfileId));
//
//        Profile user = profileRepository.findById(chatMessage.authorId())
//                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find a profile with ID : "+chatMessage.authorId()));
//        //TODO
//
//        ChatMessage messageWithTime = new ChatMessage(chatMessage.messageText(), chatMessage.authorId());
////                ,LocalDateTime.now());//here we copy the chatmessage to change the time according to recievers timezone
//        conversation.messages().add(messageWithTime);
//        conversationService.generateProfileResponse(conversation,profile,user);
//        conversationRepository.save(conversation);
//        System.out.println("Conversation ID: " + conversationId);
//        return conversation;
//    }
//
//
//    public record CreateConversationRequest(
//            String profileId
//    ) {}
//
//}





@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(
            @PathVariable String conversationId
    ) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
    ) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
        String matchProfileId = conversation.profileId();

        Profile profile = profileRepository.findById(matchProfileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + matchProfileId
                ));
        Profile user = profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + chatMessage.authorId()
                ));

        // TODO: Need to validate that the author of a message happens to be only the profile associated with the message or the user ONLY

        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        conversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        return conversation;

    }
}
