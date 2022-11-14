package org.tecky.nohorny.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.tecky.nohorny.services.intf.ICoreOauthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    ICoreOauthService iCoreOauthService;

    @GetMapping("/index")
    public String index(@RequestParam Map<String, String> requestParam, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

//        if(requestParam.get("oauth") == null){
//
//            return "index";
//        }
//
//        Optional<Cookie> cookie = Arrays
//                .stream(request.getCookies())
//                .filter(element -> element.getName().equals("AccessToken"))
//                .findFirst();
//
//        if(cookie.isEmpty()){
//
//            return "index";
//        }

        RestTemplate restTemplate = new RestTemplate();

        String json = restTemplate.getForObject("http://47.92.137.0:9001/api/res/user/profile", String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> mapInfo = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        ResponseEntity<?> responseEntity = iCoreOauthService.loginCoreUser(mapInfo.get("username"), mapInfo.get("email"));

        return "redirect:/index.html";

    }



}
