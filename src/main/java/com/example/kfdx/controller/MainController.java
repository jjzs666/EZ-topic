package com.example.kfdx.controller;

import com.example.kfdx.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Jame
 * @date : 2022-05-19 08:54
 **/
@RestController
public class MainController {

    @Autowired
    private ParseService parseService;

    @GetMapping("/getAnswer")
    public Object getAnswer(String s) {
        return parseService.getTopic(s);
    }

}
