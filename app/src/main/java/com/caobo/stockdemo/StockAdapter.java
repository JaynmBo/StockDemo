package com.caobo.stockdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caobo.stockdemo.bean.StockBean;
import com.caobo.stockdemo.view.CustomizeScorllView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cb
 * on 2020-06-04.
 */
public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private List<StockBean> stockBeans;

    private Context mContext;

    public StockAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setStockBeans(List<StockBean> stockBeans) {
        this.stockBeans = stockBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mStockName.setText(stockBeans.get(position).getStockName());
        holder.mStockRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        holder.mStockRecyclerView.setNestedScrollingEnabled(false);

        // TODO：文本RecyclerView中具体信息的RecyclerView（RecyclerView嵌套）
        StockItemAdapter stockItemAdapter = new StockItemAdapter(mContext);
        holder.mStockRecyclerView.setAdapter(stockItemAdapter);
        stockItemAdapter.setDetailBeans(stockBeans.get(position).getDetail());

    }

    @Override
    public int getItemCount() {
        return stockBeans.size() == 0 ? 0 : stockBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mStockName;
        CustomizeScorllView mStockScrollView;
        RecyclerView mStockRecyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mStockName = itemView.findViewById(R.id.stockName);
            mStockScrollView = itemView.findViewById(R.id.stockScrollView);
            mStockRecyclerView = itemView.findViewById(R.id.stockRecyclerView);

        }
    }
}
