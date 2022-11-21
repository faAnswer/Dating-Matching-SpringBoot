package org.tecky.nohorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tecky.nohorny.entities.PmEntity;
import org.tecky.nohorny.entities.PmStatusEntity;
import org.tecky.nohorny.livechat.dto.LiveChatContactDTO;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.mapper.PmEntityRepository;
import org.tecky.nohorny.mapper.PmStatusEntityRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class HelloController {

    @Autowired
    PmEntityRepository pmEntityRepository;

    @Autowired
    PmStatusEntityRepository pmStatusEntityRepository;

    @Autowired
    ILiveChatService iLiveChatService;



    @GetMapping("/hello")
    public String hello(Authentication authentication){

//        List<Integer> uidList = new ArrayList<>();
//
//        uidList.add(336);
//        uidList.add(337);
//
//        List<LiveChatContactDTO> pmTest = iLiveChatService.getAllContacts(authentication);
//
//
//        PmStatusEntity pmStatusEntity = pmStatusEntityRepository.findByPmidAndIsreadIs(68, 0);


        return "Hello World";

    }

}
