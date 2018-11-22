package com.jzd.android.jon.widget.popupwindow;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.android.jon.R;
import com.jzd.android.jon.core.module.jmap.JMapImpl;
import com.jzd.android.jon.core.ui.JBaseActivity;
import com.jzd.android.jon.utils.JMetrics;

import java.util.List;

/**
 * 列表弹出窗口
 */
public class JListPopupWindow extends JBasePopupWindow
{

    private OnItemClickListener mOnItemClickListener;
    private List<JMapImpl> mData;
    private JBaseActivity mActivity;

    /**
     * 初始化
     */
    public JListPopupWindow(JBaseActivity activity, List<JMapImpl> data, OnItemClickListener onItemClickListener)
    {
        super(activity, null);

        RecyclerView mRvData = new RecyclerView(activity);
        int dp2px = JMetrics.INSTANCE.dp2px(8);
        mRvData.setPadding(dp2px, 0, dp2px, 0);
        mActivity = activity;
        setView(mRvData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mRvData.setLayoutManager(layoutManager);
        mRvData.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        mRvData.addItemDecoration(dividerItemDecoration);
        ListAdapter adapter = new ListAdapter();
        mData = data;
        setOnItemClickListener(onItemClickListener);
        mRvData.setAdapter(adapter);
    }

    private int mListGravity = Gravity.CENTER;

    public JListPopupWindow setListGravity(int gravity)
    {
        mListGravity = gravity;
        return this;
    }

    /**
     * 弹窗里的列表点击事件
     */
    public JListPopupWindow setOnItemClickListener(OnItemClickListener itemClickListener)
    {
        this.mOnItemClickListener = itemClickListener;
        return this;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position, JMapImpl obj);
    }

    private class ListAdapter extends RecyclerView.Adapter<ListViewHolder> implements View.OnClickListener
    {

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(mActivity)
                    .inflate(R.layout.rv_item_simple, parent, false);
            view.setOnClickListener(this);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position)
        {
            holder.mTvItem.setGravity(mListGravity);
            holder.itemView.setTag(position);
            holder.mTvItem.setText(mData.get(position)
                    .value()
                    .toString());
        }

        @Override
        public int getItemCount()
        {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public void onClick(View v)
        {
            if(mOnItemClickListener != null)
            {
                int position = (int) v.getTag();
                mOnItemClickListener.onItemClick(position, mData.get(position));
                dismiss();
            }
        }
    }

    private class ListViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTvItem;

        ListViewHolder(View itemView)
        {
            super(itemView);
            mTvItem = itemView.findViewById(R.id.mTvItem);
        }
    }
}
