package com.bkjk.infra.test.timeline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bkjk.infra.test.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/26
 * Version: 1.0.0
 * Description:
 */
public class TimeLineAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, Object>> mItemLists;

    public TimeLineAdapter(Context context, ArrayList<HashMap<String, Object>> itemLists) {
        mItemLists = itemLists;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.time_line_layout_item, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTitleTv.setText((String) mItemLists.get(position).get("ItemTitle"));
        holder.mContentTv.setText((String) mItemLists.get(position).get("ItemContent"));
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title_tv)
        TextView mTitleTv;
        @BindView(R.id.item_text_tv)
        TextView mContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
