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



    public Result(Integer code, Integer correctAnswerIndex, Object data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public static Object succeed(Integer correctAnswerIndex) {
        return JSONObject.toJSON(new Result(200, correctAnswerIndex, null, null));
    }

    //成功-数据
    public static Object succeed(String data) {
        return JSONObject.toJSON(new Result(200, -1, data, null));
    }


    public static Object fail() {
        return JSONObject.toJSON(new Result(500, -1, null, null));
    }

    public static Object fail(String message) {
        return JSONObject.toJSON(new Result(500, -1, null, message));
    }



}