package work.jame.topic.controller;

import com.alibaba.fastjson.JSON;
import work.jame.topic.pojo.Topic;
import work.jame.topic.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import work.jame.topic.service.ParseServiceJST;
import work.jame.topic.util.Result;

/**
 * @author : Jame
 * @date : 2022-05-19 08:54
 **/
@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @Autowired
    private ParseServiceJST parseServiceJST;

    @PostMapping("/getAnswer")
    public Object getAnswer(@RequestBody Topic topic) {
        if (topic == null) {
            return JSON.toJSONString(Result.failed("topic is null!"));
        }
        return JSON.toJSONString(mainService.invoke(topic));
    }

    @PostMapping("/testJST")
    public Object testJST(@RequestBody Topic topic) {
        return JSON.toJSONString(parseServiceJST.parse(topic));
    }


}
