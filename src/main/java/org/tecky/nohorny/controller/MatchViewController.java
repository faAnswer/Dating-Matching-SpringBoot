package org.tecky.nohorny.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.livechat.dto.LiveChatContactDTO;
import org.tecky.nohorny.livechat.dto.LiveChatMsgDTO;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.services.intf.IMatchService;
import org.tecky.nohorny.services.intf.IUserService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MatchViewController {

    @Autowired
    IMatchService iMatchService;

    @PostMapping("/match")
    public String match(@RequestParam Map<String, String> mapMatch , Model model, Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if(iMatchService.dailyMatch(authentication)){

            iMatchService.matching(mapMatch, authentication);
        }

        List<UserProfileDTO> matchUserList = iMatchService.getMatchUser(authentication);
        List<UserProfileDTO> mockUserList = iMatchService.getMockUser(new int[]{42, 40, 43, 44, 45, 46, 36, 37, 38, 352}, authentication);

        matchUserList.addAll(mockUserList);

        model.addAttribute("matchUserList", matchUserList);

        return "match";
    }
}