package com.caobo.stockdemo.adapter;

import android.view.View;

import com.caobo.stockdemo.view.CustomizeMarqueeView;

import java.util.List;

/**
 * 自定义Adapter基类
 * Created by 码农专栏
 * on 2020-06-06.
 */
public abstract class MarqueeViewBaseAdapter<T> {

    protected List<T> adapterData;

    private OnDataChangedListener mOnDataChangedListener;

//    public MarqueeViewBaseAdapter(List<T> data) {
//        this.adapterData = data;
//    }

    public void setData(List<T> data) {
        this.adapterData = data;
        notifyDataChanged();
    }

    public int getItemCount() {
        return adapterData == null ? 0 : adapterData.size();
    }

    public abstract View onCreateView(CustomizeMarqueeView parent);

    public abstract void onBindView(View view, int position);

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
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
