package com.smallow.badminton.sys.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by smallow on 15/11/21.
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonBaseAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position
        );
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder viewHolder, T item);


    public void setData(List<T> data, boolean notifyDataSetChanged) {
        mDatas = data;
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public T getData(int position) {
        if (mDatas != null && position >= 0 && position < mDatas.size()) {
            return mDatas.get(position);
        }
        return null;
    }


}
