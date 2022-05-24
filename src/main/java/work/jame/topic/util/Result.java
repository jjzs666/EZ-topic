package work.jame.topic.util;

import com.alibaba.fastjson.JSONObject;
import work.jame.topic.pojo.Answers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author : Jame
 * @date : 2022-05-19 13:49
 **/

/*
 返回的json格式

{
    "answers": [             //匹配答案的集合
        {
            "content": "xxx",  //匹配答案的内容
            "index": 2          //匹配答案的下标-根据出入答案的下标
        }
    ],
    "answerSimilarity": 1.0,       //寻找到答案的匹配度
    "code": 200,                //状态码
    "topicSimilarity": 0.97,    //题目的相似度
    "tryAcquireCount": 1,       //尝试获取次数
    "type": 1               //题目的类型
}

* */
public class Result {

    //http状态码
    private Integer code;
    private Object data;
    private String message;
    private Integer type;

    private Answers[] answers;
    //尝试次数
    private Integer tryAcquireCount;

    //结果的题目和原题目相似度
    private double topicSimilarity;
    //结果的答案和原答案相似度
    private double answerSimilarity;


    public Result(Integer code, Answers[] answers, Integer tryAcquireCount,
                  Object data, String message, Integer type,
                  double topicSimilarity, double answerSimilarity) {
        this.code = code;
        this.type = type;
        this.message = message;
        this.data = data;
        this.answers = answers;
        this.tryAcquireCount = tryAcquireCount;

        BigDecimal bigDecimal = new BigDecimal(topicSimilarity).setScale(2, RoundingMode.HALF_UP);
        this.topicSimilarity = bigDecimal.doubleValue();

        bigDecimal = new BigDecimal(answerSimilarity).setScale(2, RoundingMode.HALF_UP);
        this.answerSimilarity = bigDecimal.doubleValue();
    }

    /**
     *
     * @param answers 正确答案集合
     * @param tryAcquireCount 尝试次数
     * @param topicSimilarity 题目相似度
     * @param answerSimilarity 答案相似度
     * @return
     */
    public static Result succeed(Answers[] answers, Integer tryAcquireCount,  double topicSimilarity, double answerSimilarity) {
        return new Result(200, answers, tryAcquireCount, null, null, null, topicSimilarity, answerSimilarity);
    }


    /**
     * @param answers 正确答案集合
     * @param tryAcquireCount 尝试次数
     * @param type 题目类型
     * @param topicSimilarity 题目相似度
     * @param answerSimilarity 答案相似度
     * @return
     */
    public static Result succeed(Answers[] answers, Integer tryAcquireCount,Integer type,  double topicSimilarity, double answerSimilarity) {
        return new Result(200, answers, tryAcquireCount, null, null, type, topicSimilarity, answerSimilarity);
    }


    //失败-信息-尝试次数
    public static Result failed(String message, Integer tryAcquireCount) {
        return new Result(666, null, tryAcquireCount, null, message, -1, 0, 0);
    }

    //失败-信息
    public static Result failed(String message) {
        return new Result(666, null, -1, null, message, -1, 0, 0);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Answers[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answers[] answers) {
        this.answers = answers;
    }

    public Integer getTryAcquireCount() {
        return tryAcquireCount;
    }

    public void setTryAcquireCount(Integer tryAcquireCount) {
        this.tryAcquireCount = tryAcquireCount;
    }

    public double getTopicSimilarity() {
        return topicSimilarity;
    }

    public void setTopicSimilarity(double topicSimilarity) {
        this.topicSimilarity = topicSimilarity;
    }

    public double getAnswerSimilarity() {
        return answerSimilarity;
    }

    public void setAnswerSimilarity(double answerSimilarity) {
        this.answerSimilarity = answerSimilarity;
    }
}