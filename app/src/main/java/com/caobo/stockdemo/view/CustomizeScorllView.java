package com.caobo.stockdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by cb
 * on 2020-06-04.
 */
public class CustomizeScorllView extends HorizontalScrollView {

    private OnScrollViewListener viewListener;

    private interface OnScrollViewListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }

    public void setViewListener(OnScrollViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public CustomizeScorllView(Context context) {
        this(context,null);
    }

    public CustomizeScorllView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomizeScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (viewListener != null) {
            viewListener.onScroll(l, t, oldl, oldt);
        }
    }

}
