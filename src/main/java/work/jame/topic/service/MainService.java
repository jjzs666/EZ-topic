package work.jame.topic.service;

import com.alibaba.fastjson.JSONObject;
import work.jame.topic.config.SpringConfig;
import work.jame.topic.pojo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import work.jame.topic.util.Result;
import work.jame.topic.util.StringUtil;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author : Jame
 * @date : 2022-05-19 17:16
 **/
@Service
public class MainService {


    public Result invoke(Topic topic) {
        if (StringUtil.isEmpty(topic.getName())) {
            return Result.failed("提交的题目为空");
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);


        Set<Result> results = new HashSet<>();

        Map<String, ParseService> map = SpringConfig.getBeansByType(ParseService.class);
        Set<Map.Entry<String, ParseService>> entries = map.entrySet();

        for (Map.Entry<String, ParseService> entry : entries) {
            new Thread(() -> {
                try {
                    results.add(entry.getValue().parse(topic));
                } finally {
                    countDownLatch.countDown();
                }

            }).start();
        }
        try {
            boolean await = countDownLatch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Optional<Result> max = results.stream().max(Comparator.comparingDouble(x -> x.getTopicSimilarity() + x.getAnswerSimilarity()));
        return max.orElseGet(() -> Result.failed("没有找到数据"));

    }

}
