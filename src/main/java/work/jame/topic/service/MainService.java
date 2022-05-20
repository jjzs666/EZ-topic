package work.jame.topic.service;

import com.alibaba.fastjson.JSONObject;
import work.jame.topic.pojo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import work.jame.topic.util.Result;
import work.jame.topic.util.StringUtil;

/**
 * @author : Jame
 * @date : 2022-05-19 17:16
 **/
@Service
public class MainService {

    @Autowired
    @Qualifier("parseServiceTIW")
    private ParseService parseService;

    public Object invoke(Topic topic) {
        if(StringUtil.isEmpty(topic.getName())){
            return Result.failed("提交的题目为空");
        }
        return parseService.parse(topic);

    }

}
