package com.example.tinder_ai_backend.conversations;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ChatMessage(
        String messageText,

        String authorId,

//        @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss z", timezone = "GMT")
        LocalDateTime messageTime

) {
}
