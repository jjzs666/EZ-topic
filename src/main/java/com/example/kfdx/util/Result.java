package com.example.kfdx.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : Jame
 * @date : 2022-05-19 13:49
 **/
public class Result {

    //http状态码
    private Integer code;
    private Object data;


    public Result() {
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }



    //成功-数据-信息
    public static Object succeed(String data) {
        return JSONObject.toJSON(new Result(200, data));
    }


    public static Object fail(){
        return JSONObject.toJSON(new Result(500, null));
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




    public String showData() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}