package work.jame.topic.service;

import com.alibaba.fastjson.JSONObject;
import work.jame.topic.pojo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import work.jame.topic.util.Result;

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
        int i = Integer.parseInt(String.valueOf(parseService.parse(topic)));
        if (i > -1) {
            Result result = new Result(200, i,null, null);
            System.out.println(JSONObject.toJSONString(result));
            System.out.println(result);
            return Result.succeed(i);
        }
        return Result.fail();
    }

}
