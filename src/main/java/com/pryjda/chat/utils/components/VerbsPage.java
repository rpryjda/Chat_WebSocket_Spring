package com.pryjda.chat.utils.components;

import com.pryjda.chat.entity.Verb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class VerbsPage {

    private List<Verb> verbs;
    private int size;
    private int currentPage;
    private int maxPage;
}
