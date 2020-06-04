package com.caobo.stockdemo;

import android.os.Bundle;

import com.caobo.stockdemo.bean.StockBean;
import com.caobo.stockdemo.utils.AssetsUtils;
import com.caobo.stockdemo.view.CustomizeScorllView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mHeadRecyclerView;

    private RecyclerView mContentRecyclerView;

    private TabAdapter mTabAdapter;

    private StockAdapter mStockAdapter;

    private CustomizeScorllView headHorizontalScrollView;

    String values[] = {"最新", "涨幅", "涨跌", "换手", "成交额", "量比", "振幅", "测试1", "测试1"};

    private List<StockBean> stockBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadRecyclerView = findViewById(R.id.headRecyclerView);
        mContentRecyclerView = findViewById(R.id.contentRecyclerView);

        // TODO:Tab栏RecycleView
        // 设置RecyclerView水平显示
        mHeadRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mTabAdapter = new TabAdapter(this);
        // 设置ListView禁止滑动，这样使得ScrollView滑动更流畅
        mHeadRecyclerView.setNestedScrollingEnabled(false);
        mHeadRecyclerView.setAdapter(mTabAdapter);
        initTabData();

        // TODO:文中列表RecyclerView
        mContentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStockAdapter = new StockAdapter(this);
        mContentRecyclerView.setAdapter(mStockAdapter);
        initStockData();
    }


    void initTabData() {
        mTabAdapter.setTabData(Arrays.asList(values));
    }


    void initStockData() {
        String json = AssetsUtils.getJson("stock.json", getApplication());
        stockBeans = new Gson().fromJson(json, new TypeToken<List<StockBean>>() {
        }.getType());
        mStockAdapter.setStockBeans(stockBeans);
    }
}
