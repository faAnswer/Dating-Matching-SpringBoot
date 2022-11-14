package org.tecky.nohorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;

import java.util.List;

@RestController
@RequestMapping("/api/livechat")
public class LiveChatController {

    @Autowired
    ILiveChatService iLiveChatService;

    @GetMapping("/unread")
    public ResponseEntity<?> unread(Authentication authentication){

        List<String> unReadFromList = iLiveChatService.unReadMsgFrom(authentication);

        return JSONResponse.ok(unReadFromList);
    }
}