package org.tecky.nohorny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketController {


    @GetMapping("/chat")
    public String chat() {
        return "chatRoom";
    }
}