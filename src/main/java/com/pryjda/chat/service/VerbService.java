package com.pryjda.chat.service;

import com.pryjda.chat.entity.Verb;

import java.util.List;

public interface VerbService {

    List<Verb> getVerbs();

    List<Verb> getPaginatedVerbs(int page, int size);
}
