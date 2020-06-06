package com.caobo.stockdemo;

import android.os.Bundle;

import com.caobo.stockdemo.adapter.MarquessViewAdapter;
import com.caobo.stockdemo.bean.MessageBean;
import com.caobo.stockdemo.view.CustomizeMarqueeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by cb
 * on 2020-06-06.
 */
public class MarqueeActivity extends AppCompatActivity {

    private CustomizeMarqueeView mMarqueeView1;

    private MarquessViewAdapter marquessViewAdapter;
    private List<MessageBean> messageBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marquee);

        mMarqueeView1 = findViewById(R.id.marqueeView1);

        marquessViewAdapter = new MarquessViewAdapter();

        mMarqueeView1.setAdapter(marquessViewAdapter);
        initData();
        marquessViewAdapter.setMessageBeans(messageBeans);

    }


    void initData() {
        messageBeans = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            MessageBean messageBean = new MessageBean();
            messageBean.setTime("12:00");
            messageBean.setStockName("中国黄金" + i);
            messageBean.setType("主力出货");
            messageBean.setMessage("12.5万股");
            messageBeans.add(messageBean);
        }
    }
}
