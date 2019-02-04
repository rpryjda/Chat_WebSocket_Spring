package com.pryjda.chat.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime time;
    private String username;
    private String content;
}
