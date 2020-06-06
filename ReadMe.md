# 一、前言

Android 开发过程中自定义 View 真的是无处不在，随随便便一个 UI 效果，都会用到自定义 View。前面三篇文章已经讲过自定义 View 的一些案例效果，相关类和 API，还有事件分发理论知识请自行充电。作者不喜欢讲一些原理性的东西，直接上效果和源码。


本篇文章原本和自定义 View 关系不大，作者强行自定义绘制了一个小控件，以符合最近的文章主题。本文是实现股票、证券列表联动效果，

# 二、开发准备工作

## 1、先上效果图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200605181908679.gif#pic_center)

## 2、案例源码下载

## 3、案例应用知识点

1. 自定义 View 基础知识（测量、Canvas、Paint、Path）

2. HorizontalScrollView 滚动事件

3. RecyclerView 嵌套 HorizontalScrollView 冲突处理

4. 接口回调知识

5. 自定义 layer-list 和 shape

## 4、案例思路分析

根据效果图，我们可以将布局拆解，分为以下独立模块：

1. 效果图整体布局是一个 Tab 栏 + RecyclerView 列表组成

2. RecyclerView 列表 item 布局和 Tab 栏一致

3. Tab 栏水平滑动时，RecyclerView 列表同步滑动

4. RecyclerView 列表 item 滑动时，整个列表跟滚动，并且 Tab 栏也同步滚动更新
   ![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWdrci5jbi1iai51ZmlsZW9zLmNvbS8yY2M1YzI5ZC0yMGZmLTRmNjUtOWQ2Ni0xNTY5MDkyNTM0ODcucG5n?x-oss-process=image/format,png#pic_center)

# 三、代码实现

## 1、自定义 TextView

自定义 View 的基础知识这里不做回顾，如果对自定义 View 还不是很了解的朋友，可以查看之前的文章。

自定义 TextView，将效果图左上角的文本和小三角符号完成绘制工作，并设置一个背景效果。这里将属性直接在 Java 代码里设置了，建议使用自定义属性，方便在 XML 中设置。

**1. 测量 TextView 尺寸**

根据文本的尺寸和 Padding 值计算文本的宽度和高度，因为本案例中自定义 View 尺寸在 XML 中设置 wrap_content，所以主要看 switch 语句中 MeasureSpec.AT_MOST 节点，**关于 MeasureSpec.EXACTLY、MeasureSpec.AT_MOST、MeasureSpec.UNSPECIFIED 区别，请查看作者之前自定义 View 的系列文章。**

测量成功后重新设置 View 尺寸：setMeasuredDimension(width, height);

```java
/**
 * View尺寸测量
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
        case MeasureSpec.EXACTLY:// 精确尺寸或者最大值
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
```

**2. 绘制文本**

绘制文本需要注意的，下图中红色的 Baseline 是基准线，紫色的 Top 是文字的最顶部，也就是在 drawText()中指定的 x 所对应，橙色的 Bottom 是文字的底部。

![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWdrci5jbi1iai51ZmlsZW9zLmNvbS84M2RlMThkNC0xYzZmLTQ1OWEtYTg5My1iMmMyYzZjODE0ZGIucG5n?x-oss-process=image/format,png#pic_center)

**所以文本的高度：**

```java
距离 = 文字高度的一半 - 基线到文字底部的距离（也就是bottom） =
		 (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom
```

```java
// 绘制文本
Paint.FontMetrics fontMetrics = paint.getFontMetrics();
float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
canvas.drawText(tabStr, getPaddingLeft(), height / 2 + distance, paint);
```

**3. 绘制三角形**

绘制三角形需要使用 Path 相关知识，具体相关 API 方法，请读者自行补习。

主要是确定三角形的三个点 x、y 轴位置，然后调用 canvas.drawPath(path, paint)方法完成绘制工作。

```java
//绘制三角形
Path path = new Path();
path.moveTo(rect.width() + specSize + getPaddingLeft(), height / 2 - triangleSize / 2);//三角形左下角位置坐标
path.lineTo(rect.width() + specSize + getPaddingLeft(), height / 2 + triangleSize / 2);//三角形右下角位置坐标
path.lineTo(rect.width() + specSize + getPaddingLeft() + triangleSize / 2, height / 2);//三角形顶部位置坐标
path.close();
canvas.drawPath(path, paint);
```

**4. 定义自定义 View 边框**

View 背景使用 layer-list 完成，这是日常开发中最常用的功能，经常可以使用 shap 完成一些简单的背景效果，不需要每次都使用图片，而且还不会出现适配的苦恼。

```xml
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape>
            <solid android:color="@color/tabTextTitle" />
            <corners android:topRightRadius="30dp"
                android:bottomRightRadius="30dp"/>
        </shape>
    </item>
    <!-- 只设置顶部、底部、右边边框 -->
    <item
        android:bottom="3px"
        android:right="3px"
        android:top="3px">
        <shape android:shape="rectangle">
            <solid android:color="#2A2720"/>
            <corners android:topRightRadius="30dp"
                android:bottomRightRadius="30dp"/>
        </shape>
    </item>
</layer-list>
```

以上就完成了自定义 View 的全部工作，当然这不是本文的重点内容，只是顺带提一下自定义 View 的基本知识。

## 2、自定义 CustomizeScrollView

- 自定义 CustomizeScrollView 继承 HorizontalScrollView。

* 重写 onScrollChanged()方法，主要用于监听 ScrollView 滑动。

* 定义回调接口 OnScrollViewListener，用于监听 onScrollChanged()方法滚动回调。

```java
@Override
protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (viewListener != null) {
        viewListener.onScroll(l, t, oldl, oldt);
    }
}
```

CustomizeScrollView 类很简单，没有做太多事情，在 XML 中直接引用完整类名即可。

## 3、主页面布局

布局 XML 这里就不全部贴出了，比较影响文章阅读性，感兴趣的朋友可以下载源码自己研究，主要讲解下 **HorizontalScrollView+RecyclerView 嵌套问题**。

如果直接在 HorizontalScrollView 中嵌套 RecyclerView，滑动时会出现内容显示不完整的情况，相关很多朋友在开发过程中也遇到过这种问题。（Tab 栏一共有 7 个 item，但是指滑动到可见的 item，后面的无法滑动）：

![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWdrci5jbi1iai51ZmlsZW9zLmNvbS8zZTY3Zjk2OS0xMmEwLTRiZTUtODg5Yy0xMTQwYTY1MDIwNzcuZ2lm#pic_center)

在 HorizontalScrollView 中嵌套 RecyclerView 需要注意内容显示不完整的问题，**不能直接将 2 个布局嵌套，需要在 HorizontalScrollView 中添加一个 RelativeLayout 布局，并且设置属性：android:descendantFocusability="blocksDescendants"**，这样就可以完美解决嵌套导致内容显示不完整的问题。

![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWdrci5jbi1iai51ZmlsZW9zLmNvbS8wZWI0ZDU0Yy0yZGNlLTQ4ZTMtOGY1ZC00Y2M3ZTczMzNhZmYuZ2lm#pic_center)

```xml
<com.caobo.stockdemo.view.CustomizeScrollView
    android:id="@+id/headScrollView"
    android:layout_width="0dp"
    android:layout_height="50dp"
    android:layout_weight="7">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:descendantFocusability="blocksDescendants">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/headRecyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </RelativeLayout>
</com.caobo.stockdemo.view.CustomizeScrollView>
```

关于 descendantFocusability 属性简单介绍：

```java
beforeDescendants：viewgroup会优先其子类控件而获取到焦点
afterDescendants：viewgroup只有当其子类控件不需要获取焦点时才获取焦点
blocksDescendants：viewgroup会覆盖子类控件而直接获得焦点.
```

## 4、主列表 Adapter

1. 完成以上工作后，剩下主要内容都在主列表页面的适配器中完成，定义 ViewHolder 集合和记录滑动 X 轴变量：

```java
/**
 * 保存列表ViewHolder集合
 */
private List<ViewHolder> recyclerViewHolder = new ArrayList<>();
/**
 * 记录item滑动的位置，用于RecyclerView上下滚动时更新所有列表
 */
private int offestX;
```

2. 在 onBindViewHolder()方法中初始化数据，并将 ViewHolder 添加到集合中，然后水平滑动单个 Item 时，遍历 ViewHolder 使得整个列表的 HorizontalScrollView 同步滚动。

```java
/**
 * 第一步：水平滑动item时，遍历所有ViewHolder，使得整个列表的HorizontalScrollView同步滚动
 */
holder.mStockScrollView.setViewListener(new CustomizeScrollView.OnScrollViewListener() {
    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
        for (ViewHolder viewHolder : recyclerViewHolder) {
            if (viewHolder != holder) {
                viewHolder.mStockScrollView.scrollTo(l, 0);
            }
        }
    }
});
```

3. 接上上面步骤，在水平滑动 Item 时，接口回调到 Tab 栏 HorizontalScrollView，在 MainActivity 中更新 Tab 栏滚动位置，并且记录滑动的 X 轴位置（用于在后面 RecyclerView 同步 item 时使用）。

```java
/**
 * 第二步：水平滑动item时，接口回调到Tab栏的HorizontalScrollView，使得Tab栏跟随item滚动实时更新
 */
if (onTabScrollViewListener != null) {
    onTabScrollViewListener.scrollTo(l, t);
    offestX = l;
}
```

4. 完成上面步骤后，就基本已经实现在 RecyclerView 列表水平滑动，Tab 栏和其他 Item 同步更新的效果，接下面需要完成 Tab 水平滑动时，使得 RecyclerView 同步更新。**根据 Adpater 中 ViewHolder 集合遍历所有 holder 对象，并给 RecyclerView 中 item 每个 CustomizeScrollView 设置滚动方法 scrollTo()**。因为水平滚动，不会涉及 Y 轴的位置，所以案例中都只设置了 X 轴的值。

```java
/**
 * 第三步：Tab栏HorizontalScrollView水平滚动时，遍历所有RecyclerView列表，并使其跟随滚动
 */
headHorizontalScrollView.setViewListener(new CustomizeScrollView.OnScrollViewListener() {
    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
        List<StockAdapter.ViewHolder> viewHolders = mStockAdapter.getRecyclerViewHolder();
        for (StockAdapter.ViewHolder viewHolder : viewHolders) {
            viewHolder.mStockScrollView.scrollTo(l, 0);
        }
    }
});
```

5. 其实这一步是为了解决一个 Bug，当完成以上内容后，已经可以使用了，但是在上下滑动 item 的时候，发现未第一次显示 item 当中 HOrizontalScrollView 位置并未发生变化，所以在 RecyclerView 中添加 addOnScrollListener()添加，该方法在 RecyclerView 上下滑动时会监听，和第三步的做法比较类似，遍历 ViewHolder，获取 Adapter 中保存的 X 轴滑动位置变量 OffestX 完成 item 中 CustomizeScrollView 的滚动位置。

```java
/**
 * 第四步：RecyclerView垂直滑动时，遍历更新所有item中HorizontalScrollView的滚动位置，否则会出现item位置未发生变化状态
 */
mContentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        List<StockAdapter.ViewHolder> viewHolders = mStockAdapter.getRecyclerViewHolder();
        for (StockAdapter.ViewHolder viewHolder : viewHolders) {
            viewHolder.mStockScrollView.scrollTo(mStockAdapter.getOffestX(), 0);
        }
    }
});
```
# 四、总结

自定义View其实是一个需要经常去上手练习的过程，理论知识固然重要，但是如果不自己动手撸几个案例，依然无法熟练的掌握，所以给学习自定义View的朋友提个建议。

是不是很简单，其实这章内容没有什么难点，主要是对实现列表滑动以及联动的思路要清晰，其实编码很多时候，都是分析问题的思路很重要，只有思路明确，才能去一步一步完成功能。希望本文对你 Android 开发之路有所帮助！

**我是一名 Android 程序员，我喜欢编码，我喜欢分享，我喜欢 Android 。**
