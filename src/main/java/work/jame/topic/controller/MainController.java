package work.jame.topic.controller;

import work.jame.topic.pojo.Topic;
import work.jame.topic.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import work.jame.topic.util.Result;

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
        if(topic==null)
            return Result.failed("topic is null!");
        return mainService.invoke(topic);
    }

}
