package com.pryjda.chat.service;

import com.pryjda.chat.entity.Chat;
import com.pryjda.chat.entity.Verb;
import com.pryjda.chat.repository.ChatRepository;
import com.pryjda.chat.utils.components.CounterTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VerbCounterServiceImpl implements VerbCounterService {

    private final VerbService verbService;

    private final ChatRepository chatRepository;

    private final CounterTime counterTime;

    @Autowired
    public VerbCounterServiceImpl(VerbService verbService,
                                  ChatRepository chatRepository,
                                  CounterTime counterTime) {
        this.verbService = verbService;
        this.chatRepository = chatRepository;
        this.counterTime = counterTime;
    }

    @Override
    public List<Integer> countUsedVerbsByUsername(String username, int page, int size) {

        List<Verb> verbs = verbService.getPaginatedVerbs(page, size);
        List<Chat> conversations;

        if (counterTime.getStartTime() == null) {
            conversations = chatRepository.findAll();
        } else {
            conversations = chatRepository.findChatsByTimeAfter(counterTime.getStartTime());
        }
        Map<Verb, Integer> verbCounters = new LinkedHashMap<>();

        verbs.stream()
                .forEach(verb -> verbCounters.put(verb, 0));

        conversations.stream()
                .filter(item -> item.getUsername().equalsIgnoreCase(username))
                .forEach(chat -> {
                    String content = chat.getContent().toLowerCase();
                    verbs.stream()
                            .forEach(verb -> {
                                int no = 0;
                                if (content.contains(verb.getVerbBaseForm().toLowerCase()) ||
                                        content.contains(verb.getVerbContinuousForm()) ||
                                        content.contains(verb.getVerbPastSimple()) ||
                                        content.contains(verb.getVerbPastParticiple())) {
                                    no++;
                                }
                                verbCounters.put(verb, verbCounters.get(verb) + no);
                            });
                });
        return verbCounters.values().stream()
                .collect(Collectors.toList());
    }
}
