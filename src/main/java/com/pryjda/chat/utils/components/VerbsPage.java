package com.pryjda.chat.utils.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class VerbsPage {

    private int currentPage;
    private int maxPage;
}
