package org.tecky.nohorny.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.livechat.dto.LiveChatContactDTO;
import org.tecky.nohorny.livechat.dto.LiveChatMsgDTO;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.services.intf.IUserService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/livechat")
@CrossOrigin
public class LiveChatController {

    @Autowired
    ILiveChatService iLiveChatService;

    @Autowired
    IUserService iUserService;

    @GetMapping("/unread")
    public ResponseEntity<?> unread(Authentication authentication){

        List<String> unReadFromList = iLiveChatService.unReadMsgFrom(authentication);

        return JSONResponse.ok(unReadFromList);
    }
    @GetMapping("/box")
    public String chat(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JsonProcessingException {

        CurrentUserDTO currentUserDTO;
        if(authentication == null){

            currentUserDTO = iUserService.getCurrentUser();

        } else {

            currentUserDTO = iUserService.getCurrentUser(authentication);
        }

        Map<String, Object> resMap = new HashMap<>();


        resMap.put("currentUser", currentUserDTO);

        UserProfileDTO currentContactProfileDTO;

        currentContactProfileDTO = iUserService.getProfile("System");

        resMap.put("currentContact", currentContactProfileDTO);


        List<LiveChatMsgDTO> liveChatMsgDTOList = iLiveChatService.getAllMsg("System", authentication);
        resMap.put("chatMsgList", liveChatMsgDTOList);


        List<LiveChatContactDTO> liveChatContactDTOList = iLiveChatService.getAllContacts(authentication);
        resMap.put("chatContactList", liveChatContactDTOList);

        ObjectMapper objectMapper = new ObjectMapper();


        return objectMapper.writeValueAsString(resMap);
    }
}