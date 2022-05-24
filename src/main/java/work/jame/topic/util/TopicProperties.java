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
     * 存放各种网站的url
     */
    private Map<String, String> allRequestUrl;
    /**
     * 题目允许通过的最低相似度
     * 1为相同 0完全不同
     */
    private double topicAllowPassPrice;

    /**
     * 答案允许通过的最低相似度
     */
    private double answerAllowPassPrice;

    /**
     * 尝试下一题次数,默认4次
     */
    private Integer retryNextTopicCount = 4;

    public Map<String, String> getAllRequestUrl() {
        return allRequestUrl;
    }

    public void setAllRequestUrl(Map<String, String> allRequestUrl) {
        this.allRequestUrl = allRequestUrl;
    }

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
}
