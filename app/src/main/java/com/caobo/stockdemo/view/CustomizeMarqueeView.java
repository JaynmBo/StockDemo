package com.caobo.stockdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.caobo.stockdemo.R;
import com.caobo.stockdemo.adapter.MarquessViewAdapter;

import androidx.annotation.NonNull;

/**
 * 自定义跑马灯View
 * Created by 码农专栏
 * on 2020-06-06.
 */
public class CustomizeMarqueeView extends ViewFlipper implements MarquessViewAdapter.OnDataChangedListener {
    /**
     * 是否单行显示
     */
    private boolean isSingleLine;
    /**
     * 轮播间隔
     */
    private int interval = 3000;
    /**
     * 动画时间
     */
    private int animDuration = 1000;
    /**
     * 一次性显示item数目
     */
    private int itemCount = 1;

    private MarquessViewAdapter mMarqueeViewBaseAdapter;


    public CustomizeMarqueeView(Context context) {
        this(context, null);
    }

    public CustomizeMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 动画
        Animation animIn = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in);
        Animation animOut = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out);
        // 设置动画
        animIn.setDuration(animDuration);
        animOut.setDuration(animDuration);
        // 设置切换View的进入动画
        setInAnimation(animIn);
        // 设置切换View的退出动画
        setOutAnimation(animOut);
        // 设置View之间切换的时间间隔
        setFlipInterval(interval);
        // 设置在测量时是考虑所有子项，还是只考虑可见或不可见状态的子项。
        setMeasureAllChildren(false);
    }

    private void setData() {
        removeAllViews();
        int currentIndex = 0;
        // 计算数据展示完毕需要几页，根据总条目%每页条目计算得出
        int loopCount = mMarqueeViewBaseAdapter.getItemCount() % itemCount == 0 ?
                mMarqueeViewBaseAdapter.getItemCount() / itemCount :
                mMarqueeViewBaseAdapter.getItemCount() / itemCount + 1;
        // 遍历动态添加每页的View
        for (int i = 0; i < loopCount; i++) {
            // 每页单条展示
            if (isSingleLine) {
                LinearLayout parentView = new LinearLayout(getContext());
                parentView.setOrientation(LinearLayout.VERTICAL);
                parentView.setGravity(Gravity.CENTER);
                parentView.removeAllViews();
                View view = mMarqueeViewBaseAdapter.onCreateView(this);
                parentView.addView(view);
                if (currentIndex < mMarqueeViewBaseAdapter.getItemCount()) {// 绑定View
                    mMarqueeViewBaseAdapter.onBindView(view, currentIndex);
                }
                currentIndex = currentIndex + 1;
                addView(parentView);
            } else {
                LinearLayout parentView = new LinearLayout(getContext());
                parentView.setOrientation(LinearLayout.VERTICAL);
                parentView.setGravity(Gravity.CENTER);
                parentView.removeAllViews();
                // 每页显示多少条，就遍历添加几个子View
                for (int j = 0; j < itemCount; j++) {
                    View view = mMarqueeViewBaseAdapter.onCreateView(this);
                    parentView.addView(view);
                    currentIndex = getRealPosition(j, currentIndex);
                    if (currentIndex < mMarqueeViewBaseAdapter.getItemCount()) {
                        mMarqueeViewBaseAdapter.onBindView(view, currentIndex);
                    }
                }
                addView(parentView);
            }
        }
    }


    private int getRealPosition(int index, int currentIndex) {
        if ((index == 0 && currentIndex == 0) || (currentIndex == mMarqueeViewBaseAdapter.getItemCount() - 1)) {
            return 0;
        } else {
            return currentIndex + 1;
        }
    }


    public void setAdapter(MarquessViewAdapter adapter) {
        if (adapter != null) {
            this.mMarqueeViewBaseAdapter = adapter;
            mMarqueeViewBaseAdapter.setOnDataChangedListener(this);
        }
    }


    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setSingleLine(boolean singleLine) {
        isSingleLine = singleLine;
    }


    @Override
    public void onChanged() {
        setData();
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (VISIBLE == visibility) {
            startFlipping();
        } else if (GONE == visibility || INVISIBLE == visibility) {
            stopFlipping();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startFlipping();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopFlipping();
    }

}
