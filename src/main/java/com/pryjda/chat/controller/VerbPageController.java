package com.pryjda.chat.controller;

import com.pryjda.chat.service.VerbService;
import com.pryjda.chat.utils.components.VerbsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerbPageController {

    private final VerbsPage verbsPage;

    private final VerbService verbService;

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public VerbPageController(VerbsPage verbsPage,
                              VerbService verbService,
                              SimpMessageSendingOperations messagingTemplate) {
        this.verbsPage = verbsPage;
        this.verbService = verbService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/page/{pageNumber}/size/{pageSize}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setCurrentPage(@PathVariable(value = "pageNumber") int pageNumber,
                               @PathVariable(value = "pageSize") int pageSize) {
        verbsPage.setCurrentPage(pageNumber);
        verbsPage.setSize(pageSize);
        verbsPage.setVerbs(verbService.getPaginatedVerbs(pageNumber, pageSize));
        verbsPage.setMaxPage(verbService.getLastPageForPaginatedVerbs(pageSize));
        messagingTemplate.convertAndSend("/topic/verbs", verbsPage);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public VerbsPage getCurrentPage() {
        return verbsPage;
    }
}
