package org.tecky.nohorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tecky.nohorny.entities.PmEntity;
import org.tecky.nohorny.entities.PmStatusEntity;
import org.tecky.nohorny.mapper.PmEntityRepository;
import org.tecky.nohorny.mapper.PmStatusEntityRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    PmEntityRepository pmEntityRepository;

    @Autowired
    PmStatusEntityRepository pmStatusEntityRepository;


    @GetMapping("/hello")
    public String hello(){

        List<Integer> uidList = new ArrayList<>();

        uidList.add(336);
        uidList.add(337);


        PmStatusEntity pmStatusEntity = pmStatusEntityRepository.findByPmidAndIsreadIs(68, 0);

        List<PmEntity> teast = pmEntityRepository.findByFromuidInAndTouidIn(uidList, uidList);


        return "Hello World";

    }

}
