package com.caobo.stockdemo.bean;

import java.util.List;

/**
 * Created by cb
 * on 2020-06-04.
 */
public class StockBean {
    /**
     * detail : ["1","2","3","4","5","6","7","8","9","10"]
     * stockName : 捷顺科技
     */
    /**
     * 股票名称
     */
    private String stockName;
    private List<String> detail;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public List<String> getDetail() {
        return detail;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }

}
