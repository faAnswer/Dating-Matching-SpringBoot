package org.tecky.nohorny.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.services.intf.ICoreOauthService;
import org.tecky.nohorny.services.intf.IUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    ICoreOauthService iCoreOauthService;

    @Autowired
    IUserService iUserService;

    @GetMapping({"/index", "/"})
    public String index(Model model, Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        CurrentUserDTO currentUserDTO;
        if(authentication == null){

            currentUserDTO = iUserService.getCurrentUser();

        } else {

            currentUserDTO = iUserService.getCurrentUser(authentication);
        }

        model.addAttribute("currentUser", currentUserDTO);


        return "index";
    }


    @GetMapping("/oauth/login")
    public String oauthLogin(@RequestParam Map<String, String> requestParam, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {


        log.info("index controller");

        RestTemplate restTemplate = new RestTemplate();

        String json = restTemplate.getForObject("http://47.92.137.0:9001/api/res/user/profile", String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> mapInfo = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        ResponseEntity<?> responseEntity = iCoreOauthService.loginCoreUser(mapInfo.get("username"), mapInfo.get("email"));

        response.addHeader("Set-Cookie", responseEntity.getHeaders().get("Set-Cookie").get(0));

        return "redirect:/index";

    }
}
