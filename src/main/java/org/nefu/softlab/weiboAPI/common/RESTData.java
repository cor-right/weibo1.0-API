package org.nefu.softlab.weiboAPI.common;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * RESTful Data
 */
public class RESTData {



    private int code;
    private String message;
    private Object data;

    {
        this.code = 0;
        this.message = "";
        this.data = null;
    }

    // constructors
    public RESTData() {}

    public RESTData(Object data) {
        this.data = data;
    }

    public RESTData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RESTData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // getter and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
