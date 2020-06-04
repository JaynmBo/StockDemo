package com.caobo.stockdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cb
 * on 2020-06-04.
 */
public class StockItemAdapter extends RecyclerView.Adapter<StockItemAdapter.TabViewHolder> {

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
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, parent, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
        holder.mTabView.setText(detailBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return detailBeans.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder {

        TextView mTabView;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            mTabView = itemView.findViewById(R.id.tabView);
        }
    }
}




