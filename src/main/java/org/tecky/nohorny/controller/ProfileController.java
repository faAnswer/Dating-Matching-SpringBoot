package org.tecky.nohorny.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.services.intf.IUserService;

import java.lang.reflect.InvocationTargetException;

@Controller
@Slf4j
@CrossOrigin
public class ProfileController {

    @Autowired
    IUserService userService;

    @Autowired
    IUserService iUserService;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        CurrentUserDTO currentUserDTO;
        if(authentication == null){

            currentUserDTO = iUserService.getCurrentUser();

        } else {

            currentUserDTO = iUserService.getCurrentUser(authentication);
        }

        model.addAttribute("currentUser", currentUserDTO);


        UserProfileDTO userProfileDTO = userService.getProfile(authentication);

        model.addAttribute("profile", userProfileDTO);

        return "profile";
    }

}
