package com.naf.groupbuying.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */

public abstract class LvCommonAdapter<T> extends BaseAdapter{
    private List<T> mDatas=new ArrayList<>();

    public LvCommonAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
