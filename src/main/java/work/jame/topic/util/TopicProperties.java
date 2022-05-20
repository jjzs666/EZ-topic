package work.jame.topic.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
    private String[] url;
    /**
     * 题目允许通过的最低相似度
     * 1为相同 0完全不同
     */
    private double topicAllowPassPrice;

    /**
     * 题目允许通过的最低相似度
     */
    private double answerAllowPassPrice;

    /**
     * 尝试下一题次数,默认4次
     */
    private Integer retryNextTopicCount=4;




    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
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
