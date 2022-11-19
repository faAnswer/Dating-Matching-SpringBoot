package org.tecky.nohorny.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.livechat.dto.LiveChatMsgDTO;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.services.intf.IUserService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
@Slf4j
public class ChatViewController {

    @Autowired
    ILiveChatService iLiveChatService;
    @Autowired
    IUserService iUserService;
    @GetMapping("/chatlogin")
    public String index(Model model) {


        return "chatlogin";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("user") String user, Model model, Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        CurrentUserDTO currentUserDTO;
        if(authentication == null){

            currentUserDTO = iUserService.getCurrentUser();

        } else {

            currentUserDTO = iUserService.getCurrentUser(authentication);
        }

        model.addAttribute("currentUser", currentUserDTO);


        List<LiveChatMsgDTO> liveChatMsgDTOList = iLiveChatService.getAllMsg(user, authentication);
        model.addAttribute("chatMsgList", liveChatMsgDTOList);

        return "chat";
    }

}

