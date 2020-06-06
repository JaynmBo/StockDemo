package com.caobo.stockdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.caobo.stockdemo.R;
import com.caobo.stockdemo.bean.MessageBean;
import com.caobo.stockdemo.view.CustomizeMarqueeView;

import java.util.List;

/**
 * Created by cb
 * on 2020-06-06.
 */
public class MarquessViewAdapter extends MarqueeViewBaseAdapter<MessageBean>{

    private List<MessageBean> messageBeans;


    public void setMessageBeans(List<MessageBean> messageBeans) {
        this.messageBeans = messageBeans;
        setData(messageBeans);
    }


    @Override
    public View onCreateView(CustomizeMarqueeView parent) {
        //跑马灯单个显示条目布局，自定义
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.marqueeview_item, null);
    }

    @Override
    public void onBindView(View view, int position) {

        TextView timeTextView = view.findViewById(R.id.time);
        TextView stockNameTextView = view.findViewById(R.id.stockName);
        TextView typeTextView = view.findViewById(R.id.type);
        TextView messageTextView = view.findViewById(R.id.message);

        timeTextView.setText(messageBeans.get(position).getTime());
        stockNameTextView.setText(messageBeans.get(position).getStockName());
        typeTextView.setText(messageBeans.get(position).getType());
        messageTextView.setText(messageBeans.get(position).getMessage());

    }
}
