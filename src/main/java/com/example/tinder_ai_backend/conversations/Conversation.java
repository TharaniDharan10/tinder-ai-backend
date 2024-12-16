package com.example.tinder_ai_backend.conversations;

import com.example.tinder_ai_backend.profiles.Profile;

import java.util.List;

public record  Conversation(
        String id,  //conversation id
        String profileId,   //profile we r having conversation with
        List <ChatMessage> messages
) {

}
