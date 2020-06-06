package com.caobo.stockdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caobo.stockdemo.R;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 码农专栏
 * on 2020-06-04.
 */
public class StockItemAdapter extends RecyclerView.Adapter<StockItemAdapter.ItemViewHolder> {

    private List<String> detailBeans;

    private Context mContext;

    public StockItemAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setDetailBeans(List<String> detailBeans) {
        this.detailBeans = detailBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content_detail, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.mTabView.setText(detailBeans.get(position));
        // 模拟文本显示颜色
        int random = new Random().nextInt(3);
        if (random == 1) {
            holder.mTabView.setTextColor(mContext.getResources().getColor(R.color.tabTextColor));// 灰色
        } else if (random == 2) {
            holder.mTabView.setTextColor(mContext.getResources().getColor(R.color.greenColor));// 绿色
        } else {
            holder.mTabView.setTextColor(mContext.getResources().getColor(R.color.redColor));// 红色
        }
    }

    @Override
    public int getItemCount() {
        return detailBeans.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTabView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTabView = itemView.findViewById(R.id.tabView);
        }
    }
}




