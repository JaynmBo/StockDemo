package com.caobo.stockdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.caobo.stockdemo.R;

import androidx.annotation.Nullable;

/**
 * Created by 码农专栏
 * on 2020-06-05.
 */
@SuppressLint("AppCompatCustomView")
public class CustomizeTextView extends View {
    /**
     * 测量文本对应的矩形尺寸
     */
    private Rect rect;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 文本尺寸
     */
    private int mTextSize;
    /**
     * 文本颜色
     */
    private int mTextColor;
    /**
     * 三角形尺寸
     */
    private int triangleSize = 30;
    /**
     * 文本和三角形间距
     */
    private int specSize = 10;
    /**
     * View宽度
     */
    private int width = 0;
    /**
     * View高度
     */
    private int height = 0;
    /**
     * 文本信息
     */
    private String tabStr = "股票列表";

    public CustomizeTextView(Context context) {
        this(context, null);
    }

    public CustomizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //TODO：这些属性建议工作中设置为自定义属性，方便XML中修改
        rect = new Rect();
        paint = new Paint();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics());
        mTextColor = getResources().getColor(R.color.tabTextTitle);
        paint.setTextSize(mTextSize);
        paint.setColor(mTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getTextBounds(tabStr, 0, tabStr.length(), rect);
    }

    /**
     * View尺寸测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 宽度测量
        width = setMeasureSize(widthMeasureSpec, 1);
        // 高度测量
        height = setMeasureSize(heightMeasureSpec, 2);
        // 设置测量后的尺寸
        setMeasuredDimension(width, height);
    }

    int setMeasureSize(int measureSpec, int type) {
        int specSize = 0;
        int measurementSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            // 精确尺寸或者最大值
            case MeasureSpec.EXACTLY:
                specSize = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                if (type == 1) {
                    measurementSize = rect.width() + getPaddingLeft() + getPaddingRight() + specSize + triangleSize;
                } else if (type == 2) {
                    measurementSize = rect.height() + getPaddingTop() + getPaddingBottom();
                }
                specSize = Math.min(measurementSize, size);
                break;
        }
        return specSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制文本
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(tabStr, getPaddingLeft(), height / 2 + distance, paint);

        //绘制三角形
        Path path = new Path();
        path.moveTo(rect.width() + specSize + getPaddingLeft(), height / 2 - triangleSize / 2);//三角形左下角位置坐标
        path.lineTo(rect.width() + specSize + getPaddingLeft(), height / 2 + triangleSize / 2);//三角形右下角位置坐标
        path.lineTo(rect.width() + specSize + getPaddingLeft() + triangleSize / 2, height / 2);//三角形顶部位置坐标
        path.close();
        canvas.drawPath(path, paint);
    }
}
