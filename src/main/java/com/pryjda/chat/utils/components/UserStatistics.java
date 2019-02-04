package com.pryjda.chat.utils.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserStatistics {

    private String usernameNo1;
    private String usernameNo2;
    private List<Integer> verbOccurrenceNo1;
    private List<Integer> verbOccurrenceNo2;
    private Integer conversationNumber;
}
