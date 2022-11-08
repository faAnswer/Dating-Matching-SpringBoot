package org.tecky.nohorny.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.services.intf.IRegService;

import java.util.Map;

@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    IRegService iRegService;

    @PostMapping("/register")
    public ResponseEntity<JSONResponse<Object>> register(@RequestBody Map<String, String> userInfo){

        if(userInfo.get("username") == null){

            return JSONResponse.fail("Username must not be null", HttpStatus.BAD_REQUEST);
        }

        if(userInfo.get("password") == null){

            return JSONResponse.fail("Password must not be null", HttpStatus.BAD_REQUEST);
        }

        if(userInfo.get("email") == null){

            return JSONResponse.fail("Email must not be null", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userInfo.get("username"));
        userEntity.setShapassword(userInfo.get("password"));
        userEntity.setEmail(userInfo.get("email"));

        return iRegService.regNewUser(userEntity);
    }
}
