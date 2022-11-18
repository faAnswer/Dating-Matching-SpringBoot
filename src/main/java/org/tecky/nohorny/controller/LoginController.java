package org.tecky.nohorny.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.tecky.nohorny.services.intf.ICoreOauthService;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String index(Model model) {


        return "login";
    }
}