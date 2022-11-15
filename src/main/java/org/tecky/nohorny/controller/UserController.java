package org.tecky.nohorny.controller;


import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.services.intf.ILikeService;
import org.tecky.nohorny.services.intf.IRegService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    IRegService iRegService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ILikeService iLikeService;

    @GetMapping(value = "/like")
    public ResponseEntity<?> like(@RequestParam Map<String, String> userInfo, Authentication authentication) throws Exception {

        String toUserName = userInfo.get("username");

        if(iLikeService.changeLikeStatus(authentication,toUserName)){

            return JSONResponse.ok("like");

        } else {

            return JSONResponse.ok("nolike");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userInfo, HttpServletRequest request) throws Exception{

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

        iRegService.regNewUser(userEntity);

        return login(userInfo, request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userInfo, HttpServletRequest request) throws Exception{

        log.info("authLogin");

        authenticate(userInfo.get("username"), userInfo.get("password"), request);

        return JSONResponse.ok(userInfo.get("username"));
    }

    private void authenticate(String username, String password, HttpServletRequest request) throws Exception {
        try {

            //為了方便我們訪問 SecurityContext 對像, Spring Security 提供了 SecurityContextHolder 類,
            //通過 SecurityContextHolder.getContext() 即可獲取 SecurityContext 對像.
            //SecurityContextHolder 采用 ThreadLocal 方式存儲 SecurityContext 對像,
            //這樣就能保證我們調用 SecurityContextHolder.getContext() 得到的永遠是當前用戶的 SecurityContext.

            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
