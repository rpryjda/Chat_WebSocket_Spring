package com.pryjda.chat.utils.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConnectedUsers {

    private List<String> connectedUsers = new ArrayList<>();
}
