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

        List<MatchPerson> matchPersonList = new ArrayList<>();

        MatchPerson matchPerson1 = new MatchPerson();

        matchPerson1.setUid(1);
        matchPerson1.setName("Test1");
        matchPerson1.setAvatar("Testing1");

        MatchPerson matchPerson2 = new MatchPerson();

        matchPerson2.setUid(2);
        matchPerson2.setName("Test2");
        matchPerson2.setAvatar("Testing2");

        MatchPerson matchPerson3 = new MatchPerson();

        matchPerson3.setUid(3);
        matchPerson3.setName("Test3");
        matchPerson3.setAvatar("Testing3");

        matchPersonList.add(matchPerson1);
        matchPersonList.add(matchPerson2);
        matchPersonList.add(matchPerson3);

        model.addAttribute("matchPersonList", matchPersonList);

        return "match";
    }

}
