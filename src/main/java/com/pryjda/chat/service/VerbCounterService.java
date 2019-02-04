package com.pryjda.chat.service;

import java.util.List;

public interface VerbCounterService {

    List<Integer> countUsedVerbsByUsername(String username);
}
