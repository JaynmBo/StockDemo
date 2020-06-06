package com.caobo.stockdemo.bean;

/**
 * 跑马灯实体
 * Created by cb
 * on 2020-06-06.
 */
public class MessageBean {

    private String time;

    private String stockName;

    private String type;

    private String message;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
