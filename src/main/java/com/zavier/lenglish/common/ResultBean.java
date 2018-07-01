package com.zavier.lenglish.common;

import java.io.Serializable;
import lombok.Data;

/**
 * Controller 层统一返回实体类
 * @param <T> 返回内容的数据类型
 *
 */
@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code = -1;
    private boolean success = true;
    private String msg = "SUCCESS";
    private T data;

    private ResultBean() {
    }

    private ResultBean(boolean success) {
        this.success = success;
    }

    private ResultBean(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    private ResultBean(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    private ResultBean(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultBean<T> createBySuccess() {
        return new ResultBean<>(true);
    }

    public static <T> ResultBean<T> createBySuccessMessage(String msg) {
        return new ResultBean<>(true, msg);
    }

    public static <T> ResultBean<T> createBySuccess(T data) {
        return new ResultBean<>(true, data);
    }

    public static <T> ResultBean<T> createBySuccess(String msg, T data) {
        return new ResultBean<>(true, msg, data);
    }

    public static <T> ResultBean<T> createByError(){
        return new ResultBean<>(false);
    }

    public static <T> ResultBean<T> createByErrorMessage(String errorMessage){
        return new ResultBean<>(false,errorMessage);
    }

    public static <T> ResultBean<T> createByNotLogin() {
        return new ResultBean<>(false, "未登录");
    }

    public static <T> ResultBean<T> createByNotLoginMessage(String message) {
        return new ResultBean<>(false, message);
    }
}
