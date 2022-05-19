package com.example.kfdx.controller;

import com.example.kfdx.pojo.Topic;
import com.example.kfdx.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Jame
 * @date : 2022-05-19 08:54
 **/
@RestController
public class MainController {

    @Autowired
    private ParseService parseService;

    @PostMapping("/getAnswer")
    public Object getAnswer(@RequestBody Topic topic) {
        return parseService.getTopic(topic);
    }

}
