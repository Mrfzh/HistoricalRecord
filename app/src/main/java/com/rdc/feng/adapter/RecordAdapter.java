package com.rdc.feng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rdc.feng.contract.IHistoryRecordContract;
import com.rdc.feng.customsearchview.R;

import java.util.List;

/**
 * @author Feng Zhaohao
 * Created on 2018/7/21
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

    private Context mContext;
    private List<String> mRecordDataList;
    private IHistoryRecordContract.Presenter mPresenter;

    public RecordAdapter(Context mContext, List<String> mRecordDataList, IHistoryRecordContract.Presenter mPresenter) {
        this.mContext = mContext;
        this.mRecordDataList = mRecordDataList;
        this.mPresenter = mPresenter;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView contentTextView;
        ImageView deleteImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rv_item_record);
            contentTextView = itemView.findViewById(R.id.tv_item_record_content);
            deleteImageView = itemView.findViewById(R.id.iv_item_record_delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_record, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mRecordDataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.contentTextView.setText(mRecordDataList.get(position));
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteRecord(mRecordDataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordDataList.size();
    }
}
