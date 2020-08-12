package com.timeless.sunlightcommoncore.bean;

public class Result<T> {

    public static final int FAILURE = 100;//操作失败
    public static final int NO_MAPPING = 101;//Not Found 请求资源不存在
    public static final int MethodNotSupport = 102;//请求方法错误
    public static final int ERROR = 110;//请求发生错误，请联系开发人员
    public static final int SUCCESS = 200;//请求成功
    public static final int REQUIRED = 300;//参数为空
    public static final int PARAMETER_INVALID = 301;//参数错误
    public static final int NO_TOKEN = 400;//没有提供凭证
    public static final int EXPIRE = 401;//凭证过期
    public static final int ILLEGAL_TOKEN = 402;//凭证不合法
    public static final int EMPTY_DATA = 5000;//暂无数据

    private int key;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int key, String message, T data) {
        this.key = key;
        this.message = message;
        this.data = data;
    }

    public Result(int key, String message) {
        this.key = key;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public static Result returnSuccess() {
        return new Result(Result.SUCCESS, "请求成功");
    }

    public Result returnSuccessWithData(T data) {
        return new Result(Result.SUCCESS, "请求成功", data);
    }
}