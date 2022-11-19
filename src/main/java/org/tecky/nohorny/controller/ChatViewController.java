package org.tecky.nohorny.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ChatViewController {

    @GetMapping("/chatlogin")
    public String index(Model model) {


        return "chatlogin";
    }

    @GetMapping("/chat")
    public String chat(Model model) {


        return "chat";
    }

}

