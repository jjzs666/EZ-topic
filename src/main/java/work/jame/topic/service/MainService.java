package work.jame.topic.service;

import com.alibaba.fastjson.JSONObject;
import work.jame.topic.config.SpringConfig;
import work.jame.topic.pojo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import work.jame.topic.util.Result;
import work.jame.topic.util.StringUtil;
import work.jame.topic.util.TopicProperties;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Jame
 * @date : 2022-05-19 17:16
 **/
@Service
public class MainService {

    @Autowired
    private TopicProperties properties;


    public Result invoke(Topic topic) {
        if (StringUtil.isEmpty(topic.getName())) {
            return Result.failed("提交的题目为空");
        }

        CountDownLatch countDownLatch = new CountDownLatch(2);


        Set<Result> results = new HashSet<>();

        Map<String, ParseService> map = SpringConfig.getBeansByType(ParseService.class);
        Set<Map.Entry<String, ParseService>> entries = map.entrySet();


        for (Map.Entry<String, ParseService> entry : entries) {
            new Thread(() -> {
                synchronized (MainService.class) {
                    try {
                        Result parse = entry.getValue().parse(topic);
                        if (parse.getCode() == 200) {
                            results.add(parse);
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }

            }).start();
        }

        boolean complete;

        try {
            complete = countDownLatch.await(properties.getWaitTime(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(results);
        Optional<Result> max = results.stream().max((x, y) -> {
            Double xv = x.getAnswerSimilarity() + x.getTopicSimilarity();
            Double yv = y.getAnswerSimilarity() + y.getTopicSimilarity();
            return xv.compareTo(yv);
        });
        if (max.isPresent()) {
            Result result = max.get();
            result.setComplete(complete);
            return result;
        }
        Result failed = Result.failed("查询没有结果");
        failed.setComplete(complete);
        return failed;


    }

}
