package work.jame.topic.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author : Jame
 * @date : 2022-05-19 19:32
 * @description :
 **/
@ConfigurationProperties(prefix = "ez.application")
public class TopicProperties {

    /**
     * 题目允许通过的最低相似度
     * 1为相同 0完全不同
     */
    private double topicAllowPassPrice = 0.85;

    /**
     * 答案允许通过的最低相似度
     */
    private double answerAllowPassPrice = 0.85;

    /**
     * 尝试下一题次数
     */
    private Integer retryNextTopicCount = 3;

    /**
     * 访问页面间隔时间
     */
    private Long intervalTime = 800L;

    /**
     * 最大等待寻找答案时间
     */
    private Long waitTime = 4000L;
    /**
     * 指定不使用那个服务
     */
    private List<String> excludeService;


    public double getTopicAllowPassPrice() {
        return topicAllowPassPrice;
    }

    public void setTopicAllowPassPrice(double topicAllowPassPrice) {
        this.topicAllowPassPrice = topicAllowPassPrice;
    }

    public double getAnswerAllowPassPrice() {
        return answerAllowPassPrice;
    }

    public void setAnswerAllowPassPrice(double answerAllowPassPrice) {
        this.answerAllowPassPrice = answerAllowPassPrice;
    }

    public Integer getRetryNextTopicCount() {
        return retryNextTopicCount;
    }

    public void setRetryNextTopicCount(Integer retryNextTopicCount) {
        this.retryNextTopicCount = retryNextTopicCount;
    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public List<String> getExcludeService() {
        return excludeService;
    }

    public void setExcludeService(List<String> excludeService) {
        this.excludeService = excludeService;
    }
}
