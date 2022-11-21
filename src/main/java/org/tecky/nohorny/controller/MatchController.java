package org.tecky.nohorny.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.services.intf.IMatchService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
@CrossOrigin
@Slf4j
public class MatchController {

    @Autowired
    IMatchService iMatchService;

    @GetMapping("/result")
    public String match(@RequestParam Map<String, String> mapMatch, Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JsonProcessingException {

        if(iMatchService.dailyMatch(authentication)){

            iMatchService.matching(mapMatch, authentication);
        }

        List<UserProfileDTO> matchUserList = iMatchService.getMatchUser(authentication);
        List<UserProfileDTO> mockUserList = iMatchService.getMockUser(new int[]{36, 37, 38}, authentication);

        matchUserList.addAll(mockUserList);

        ObjectMapper objectMapper = new ObjectMapper();


        return objectMapper.writeValueAsString(matchUserList);
    }
}