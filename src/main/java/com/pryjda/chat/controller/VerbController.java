package com.pryjda.chat.controller;

import com.pryjda.chat.entity.Verb;
import com.pryjda.chat.service.VerbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VerbController {

    private final VerbService verbService;

    @Autowired
    public VerbController(VerbService verbService) {
        this.verbService = verbService;
    }

    @GetMapping("/verbs")
    @ResponseStatus(HttpStatus.OK)
    public List<Verb> getVerbs() {
        return verbService.getVerbs();
    }

    @GetMapping("/verbs/page/{number}")
    @ResponseStatus(HttpStatus.OK)
    public List<Verb> getVerbs(@PathVariable(value = "number") int no) {
        return verbService.getPaginatedVerbs(no, 10);
    }
}
