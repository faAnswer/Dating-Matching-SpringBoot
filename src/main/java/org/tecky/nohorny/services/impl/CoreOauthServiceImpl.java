package org.tecky.nohorny.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.services.intf.ICoreOauthService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CoreOauthServiceImpl implements ICoreOauthService {

    private final String passwordSait = "ABCDEF";


    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Override
    public ResponseEntity<?> loginCoreUser(String username, String email) throws JsonProcessingException {

        UserEntity userEntity =  userEntityRespostity.findByUsername(username);

        Map<String, String> userInfo = new HashMap<>();

        userInfo.put("username", username);
        userInfo.put("email", email);
        userInfo.put("password", passwordSait + username);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        if(userEntity == null) {

            return restTemplate.postForEntity("http://47.92.137.0:9999/api/user/register", request, String.class);
        } else {

            return restTemplate.postForEntity("http://47.92.137.0:9999/api/user/login", request, String.class);
        }





    }
}
