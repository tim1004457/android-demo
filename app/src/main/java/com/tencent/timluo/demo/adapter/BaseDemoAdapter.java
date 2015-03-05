package com.tencent.timluo.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/10/26.
 */
public abstract class BaseDemoAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDataList;

    public void refresh(List<T> dataList) {
        if (dataList != null) {
            if (mDataList == null)
                mDataList = new ArrayList<T>();
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public BaseDemoAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override public T getItem(int position) {
        if (position>=0 && position<getCount())
            return mDataList.get(position);
        return null;
    }

    @Override public long getItemId(int position) {
        return position;
    }
}
