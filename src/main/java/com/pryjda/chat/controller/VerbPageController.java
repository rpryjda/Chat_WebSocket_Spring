package com.pryjda.chat.controller;

import com.pryjda.chat.service.VerbService;
import com.pryjda.chat.utils.components.VerbsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerbPageController {

    private final VerbsPage verbsPage;

    private final VerbService verbService;

    @Autowired
    public VerbPageController(VerbsPage verbsPage, VerbService verbService) {
        this.verbsPage = verbsPage;
        this.verbService = verbService;
    }

    @GetMapping("/page/set-current/{number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setCurrentPage(@PathVariable(value = "number") int no) {
        verbsPage.setCurrentPage(no);
    }

    @GetMapping("/page/current")
    @ResponseStatus(HttpStatus.OK)
    public VerbsPage getCurrentPage() {
        verbsPage.setMaxPage(verbService.getLastPageForPaginatedVerbs(10));
        return verbsPage;
    }
}
