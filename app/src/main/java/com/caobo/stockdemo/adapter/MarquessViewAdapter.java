package com.caobo.stockdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.caobo.stockdemo.R;
import com.caobo.stockdemo.bean.MessageBean;
import com.caobo.stockdemo.view.CustomizeMarqueeView;

import java.util.List;

/**
 * Created by 码农专栏
 * on 2020-06-06.
 */
public class MarquessViewAdapter {

    private OnDataChangedListener mOnDataChangedListener;
    private List<MessageBean> messageBeans;
    private Context mContext;

    public MarquessViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setMessageBeans(List<MessageBean> messageBeans) {
        this.messageBeans = messageBeans;
    }

    /**
     * 自定义跑马灯的Item布局
     *
     * @param parent
     * @return
     */
    public View onCreateView(CustomizeMarqueeView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.marqueeview_item, null);
    }

    /**
     * 更新数据
     * @param view
     * @param position
     */
    public void onBindView(View view, int position) {
        TextView timeTextView = view.findViewById(R.id.time);
        TextView stockNameTextView = view.findViewById(R.id.stockName);
        TextView typeTextView = view.findViewById(R.id.type);
        TextView messageTextView = view.findViewById(R.id.message);
        timeTextView.setText(messageBeans.get(position).getTime());
        stockNameTextView.setText(messageBeans.get(position).getStockName());
        typeTextView.setText(messageBeans.get(position).getType().equals("1") ? "主力吃货" : "主力出货");
        typeTextView.setTextColor(messageBeans.get(position).getType().equals("1") ?
                mContext.getResources().getColor(R.color.redColor) : mContext.getResources().getColor(R.color.greenColor));
        messageTextView.setTextColor(messageBeans.get(position).getType().equals("1") ?
                mContext.getResources().getColor(R.color.redColor) : mContext.getResources().getColor(R.color.greenColor));
        messageTextView.setText(messageBeans.get(position).getMessage());

    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
    }

    public int getItemCount() {
        return messageBeans == null ? 0 : messageBeans.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public interface OnDataChangedListener {
        void onChanged();
    }
}
