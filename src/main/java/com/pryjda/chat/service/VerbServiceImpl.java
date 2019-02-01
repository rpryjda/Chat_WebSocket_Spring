package com.pryjda.chat.service;

import com.pryjda.chat.entity.Verb;
import com.pryjda.chat.repository.VerbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerbServiceImpl implements VerbService {

    private final VerbRepository verbRepository;

    @Autowired
    public VerbServiceImpl(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    @Override
    public List<Verb> getVerbs() {
        return verbRepository.findAll();
    }

    @Override
    public List<Verb> getPaginatedVerbs(int page, int size) {
        return verbRepository.findAllBy(PageRequest.of(page, size));
    }

}
