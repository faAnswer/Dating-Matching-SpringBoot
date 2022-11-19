package org.tecky.nohorny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.tecky.nohorny.dto.MatchPerson;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MatchController {

    @GetMapping("/match")
    public String getMatch(Model model) {


        return "match";
    }

}
