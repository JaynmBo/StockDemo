package com.caobo.stockdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caobo.stockdemo.R;
import com.caobo.stockdemo.bean.StockBean;
import com.caobo.stockdemo.view.CustomizeScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 码农专栏
 * on 2020-06-04.
 */
public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    /**
     * 保存列表ViewHolder集合
     */
    private List<ViewHolder> recyclerViewHolder = new ArrayList<>();
    /**
     * 记录item滑动的位置，用于RecyclerView上下滚动时更新所有列表
     */
    private int offestX;

    private OnTabScrollViewListener onTabScrollViewListener;

    private List<StockBean> stockBeans;

    private Context mContext;

    public StockAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnTabScrollViewListener(OnTabScrollViewListener onTabScrollViewListener) {
        this.onTabScrollViewListener = onTabScrollViewListener;
    }

    public void setStockBeans(List<StockBean> stockBeans) {
        this.stockBeans = stockBeans;
        notifyDataSetChanged();
    }

    public List<ViewHolder> getRecyclerViewHolder() {
        return recyclerViewHolder;
    }

    public int getOffestX() {
        return offestX;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mStockName.setText(stockBeans.get(position).getStockName());
        holder.mStockRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        holder.mStockRecyclerView.setNestedScrollingEnabled(false);

        // TODO：文本RecyclerView中具体信息的RecyclerView（RecyclerView嵌套）
        StockItemAdapter stockItemAdapter = new StockItemAdapter(mContext);
        holder.mStockRecyclerView.setAdapter(stockItemAdapter);
        stockItemAdapter.setDetailBeans(stockBeans.get(position).getDetail());

        if (!recyclerViewHolder.contains(holder)) {
            recyclerViewHolder.add(holder);
        }

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
                /**
                 * 第二步：水平滑动item时，接口回调到Tab栏的HorizontalScrollView，使得Tab栏跟随item滚动实时更新
                 */
                if (onTabScrollViewListener != null) {
                    onTabScrollViewListener.scrollTo(l, t);
                    offestX = l;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return stockBeans.size() == 0 ? 0 : stockBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mStockName;
        public CustomizeScrollView mStockScrollView;
        public RecyclerView mStockRecyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStockName = itemView.findViewById(R.id.stockName);
            mStockScrollView = itemView.findViewById(R.id.stockScrollView);
            mStockRecyclerView = itemView.findViewById(R.id.stockRecyclerView);
        }
    }


    public interface OnTabScrollViewListener {
        void scrollTo(int l, int t);
    }
}
