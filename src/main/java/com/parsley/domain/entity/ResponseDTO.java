package com.parsley.domain.entity;

import com.parsley.constant.OrdinaryResponseCodeConst;
import com.parsley.constant.ResponseCodeConst;

public class ResponseDTO<T>{


    protected Integer code;

    protected String msg;

    protected Boolean success;

    protected T data;

    public ResponseDTO() {
    }


    public ResponseDTO(ResponseCodeConst responseCodeConst, String msg) {
        this.code = responseCodeConst.getCode();
        this.msg = msg;
        this.success = responseCodeConst.isSuccess();
    }

    public ResponseDTO(ResponseCodeConst responseCodeConst, T data) {
        super();
        this.code = responseCodeConst.getCode();
        this.msg = responseCodeConst.getMsg();
        this.data = data;
        this.success = responseCodeConst.isSuccess();
    }

    public ResponseDTO(ResponseCodeConst responseCodeConst, T data, String msg) {
        super();
        this.code = responseCodeConst.getCode();
        this.msg = msg;
        this.data = data;
        this.success = responseCodeConst.isSuccess();
    }

    private ResponseDTO(ResponseCodeConst responseCodeConst) {
        this.code = responseCodeConst.getCode();
        this.msg = responseCodeConst.getMsg();
        this.success = responseCodeConst.isSuccess();
    }

    public ResponseDTO(ResponseDTO<T> responseDTO) {
        this.code = responseDTO.getCode();
        this.msg = responseDTO.getMsg();
        this.success = responseDTO.isSuccess();
    }

    //返回处理成功(默认消息"操作成功",状态码为1，数据为空,true)
    public static <T> ResponseDTO<T> succ() {
        return new ResponseDTO<T>(OrdinaryResponseCodeConst.SUCCESS);
    }

    //返回处理成功自定义数据和消息
    public static <T> ResponseDTO<T> succData(T data, String msg) {
        return new ResponseDTO<T>(OrdinaryResponseCodeConst.SUCCESS, data, msg);
    }

    //返回处理成功自定义数据
    public static <T> ResponseDTO<T> succData(T data) {
        return new ResponseDTO<T>(OrdinaryResponseCodeConst.SUCCESS, data);
    }

    //返回处理成功自定义消息
    public static <T> ResponseDTO<T> succMsg(String msg) {
        return new ResponseDTO<T>(OrdinaryResponseCodeConst.SUCCESS, msg);
    }

    //封装返回常量
    public static <T> ResponseDTO<T> wrap(ResponseCodeConst codeConst) {
        return new ResponseDTO<>(codeConst);
    }

    //封装返回常量，自定义数据
    public static <T> ResponseDTO<T> wrap(ResponseCodeConst codeConst, T t) {
        return new ResponseDTO<T>(codeConst, t);
    }
    //封装返回常量，自定义消息
    public static <T> ResponseDTO<T> wrap(ResponseCodeConst codeConst, String msg) {
        return new ResponseDTO<T>(codeConst, msg);
    }

    public String getMsg() {
        return msg;
    }

    public ResponseDTO<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseDTO<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseDTO<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" + "code=" + code + ", msg='" + msg + '\'' + ", success=" + success + ", data=" + data +
                '}';
    }

}
