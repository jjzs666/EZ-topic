package work.jame.topic.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : Jame
 * @date : 2022-05-19 13:49
 **/
public class Result {

    //http状态码
    private Integer code;
    private Object data;
    private String message;
    //正确答案下标
    private Integer correctAnswerIndex;

    public Result() {
    }

    public Result(Integer code, Object data, String message, Integer correctAnswerIndex) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public static Object succeed(Integer correctAnswerIndex) {
        return JSONObject.toJSON(new Result(200, null, null, correctAnswerIndex));
    }

    //成功-数据
    public static Object succeed(String data) {
        return JSONObject.toJSON(new Result(200, data, null, -1));
    }


    public static Object fail() {
        return JSONObject.toJSON(new Result(500, null, null, -1));
    }

    public static Object fail(String message) {
        return JSONObject.toJSON(new Result(500, null, message, -1));
    }


    public String showData() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}