package com.example.kfdx.controller;

import com.example.kfdx.pojo.Topic;
import com.example.kfdx.service.MainService;
import com.example.kfdx.service.ParseServiceTIW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Jame
 * @date : 2022-05-19 08:54
 **/
@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @PostMapping("/getAnswer")
    public Object getAnswer(@RequestBody Topic topic) {
        return mainService.invoke(topic);
    }

}
