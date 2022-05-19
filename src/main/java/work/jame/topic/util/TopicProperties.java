package work.jame.topic.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : Jame
 * @date : 2022-05-19 19:32
 * @description :
 **/
@ConfigurationProperties(prefix = "ez.topic")
public class TopicProperties {
    /**
     * 存放各种网站的url
     */
    private String[] url;
    /**
     * 两个字符串相似程度允许通过值,当两个字符串相似值大于等于才会通过
     * 1为相同 0完全不同
     */
    private double allowPassPrice;

    /**
     * 尝试下一题次数,默认4次
     */
    private Integer nextTopicRetryCount=4;


    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }

    public double getAllowPassPrice() {
        return allowPassPrice;
    }

    public void setAllowPassPrice(double allowPassPrice) {
        this.allowPassPrice = allowPassPrice;
    }

    public Integer getNextTopicRetryCount() {
        return nextTopicRetryCount;
    }

    public void setNextTopicRetryCount(Integer nextTopicRetryCount) {
        this.nextTopicRetryCount = nextTopicRetryCount;
    }
}
