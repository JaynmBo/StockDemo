package com.caobo.stockdemo;

import android.os.Bundle;

import com.caobo.stockdemo.adapter.MarquessViewAdapter;
import com.caobo.stockdemo.bean.MessageBean;
import com.caobo.stockdemo.utils.AssetsUtils;
import com.caobo.stockdemo.view.CustomizeMarqueeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 码农专栏
 * on 2020-06-06.
 */
public class MarqueeActivity extends AppCompatActivity {

    private CustomizeMarqueeView mMarqueeView1;
    private CustomizeMarqueeView mMarqueeView2;
    private CustomizeMarqueeView mMarqueeView3;

    private MarquessViewAdapter marquessViewAdapter1;
    private MarquessViewAdapter marquessViewAdapter2;
    private MarquessViewAdapter marquessViewAdapter3;

    private List<MessageBean> messageBeans1 = new ArrayList<>();
    private List<MessageBean> messageBeans2 = new ArrayList<>();
    private List<MessageBean> messageBeans3 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marquee);

        mMarqueeView1 = findViewById(R.id.marqueeView1);
        mMarqueeView2 = findViewById(R.id.marqueeView2);
        mMarqueeView3 = findViewById(R.id.marqueeView3);

        marquessViewAdapter1 = new MarquessViewAdapter(this);
        mMarqueeView1.setItemCount(1);
        mMarqueeView1.setSingleLine(true);
        mMarqueeView1.setAdapter(marquessViewAdapter1);
        marquessViewAdapter1.setMessageBeans(messageBeans1);
        initData1();


        marquessViewAdapter2 = new MarquessViewAdapter(this);
        mMarqueeView2.setItemCount(2);
        mMarqueeView2.setSingleLine(false);
        mMarqueeView2.setAdapter(marquessViewAdapter2);
        marquessViewAdapter2.setMessageBeans(messageBeans2);
        initData2();


        marquessViewAdapter3 = new MarquessViewAdapter(this);
        mMarqueeView3.setItemCount(3);
        mMarqueeView3.setSingleLine(false);
        mMarqueeView3.setAdapter(marquessViewAdapter3);
        marquessViewAdapter3.setMessageBeans(messageBeans3);
        initData3();
    }

    void initData1() {
        List<MessageBean> list = new Gson().fromJson(AssetsUtils.getJson("message.json", getApplication())
                , new TypeToken<List<MessageBean>>() {
                }.getType());
        messageBeans1.addAll(list);
        marquessViewAdapter1.notifyDataChanged();
    }


    void initData2() {
        List<MessageBean> list = new Gson().fromJson(AssetsUtils.getJson("message.json", getApplication()),
                new TypeToken<List<MessageBean>>() {
                }.getType());
        messageBeans2.addAll(list);
        marquessViewAdapter2.notifyDataChanged();
    }

    void initData3() {
        List<MessageBean> list = new Gson().fromJson(AssetsUtils.getJson("message.json", getApplication()),
                new TypeToken<List<MessageBean>>() {
                }.getType());
        messageBeans3.addAll(list);
        marquessViewAdapter3.notifyDataChanged();
    }

}
