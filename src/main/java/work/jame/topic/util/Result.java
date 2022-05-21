package work.jame.topic.util;

import com.alibaba.fastjson.JSONObject;
import work.jame.topic.pojo.Answers;

/**
 * @author : Jame
 * @date : 2022-05-19 13:49
 **/
public class Result {

    //http状态码
    private Integer code;
    private Object data;
    private String message;
    private Integer type;

    private Answers[] answers;
    //尝试次数
    private Integer tryAcquireCount;


    public Result(Integer code, Answers[] answers, Integer tryAcquireCount, Object data, String message,Integer type) {
        this.code = code;
        this.type=type;
        this.message = message;
        this.data = data;
        this.answers=answers;
        this.tryAcquireCount = tryAcquireCount;
    }

    public static Object succeed(Answers[] answers, Integer tryAcquireCount,Integer type) {
        return JSONObject.toJSON(new Result(200, answers, tryAcquireCount, null, null,type));
    }

    //成功-数据
    public static Object succeed(Answers[] answers, Integer tryAcquireCount, Object data, String message,Integer type) {
        return JSONObject.toJSON(new Result(200, answers, tryAcquireCount, data, message,type));
    }


    //失败-信息-尝试次数
    public static Object failed(String message, Integer tryAcquireCount) {
        return JSONObject.toJSON(new Result(666, null, tryAcquireCount, null, message,-1));
    }

    //失败-信息
    public static Object failed(String message) {
        return JSONObject.toJSON(new Result(666,  null, -1, null, message,-1));
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
}