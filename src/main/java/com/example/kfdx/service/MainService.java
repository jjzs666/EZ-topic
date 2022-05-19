package com.example.kfdx.service;

import com.example.kfdx.pojo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author : Jame
 * @date : 2022-05-19 17:16
 **/
@Service
public class MainService {

    @Autowired
    @Qualifier("parseServiceTIW")
    private ParseService parseService;

    public Object invoke(Topic topic){
        return parseService.parse(topic);
    }

}
