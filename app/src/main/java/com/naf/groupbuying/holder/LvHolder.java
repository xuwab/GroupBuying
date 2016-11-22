package com.naf.groupbuying.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/21.
 */

public class LvHolder {
    private View mConvertView;
    private SparseArray<View> mViewSparseArray;
    public LvHolder(Context context, int layoutID, ViewGroup viewGroup){
        mViewSparseArray=new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutID, viewGroup,false);
        mConvertView.setTag(this);
    }


    public static LvHolder getHolder(Context mContext,View convertView, int layoutID,ViewGroup viewGroup){
        if(convertView==null)
            return new LvHolder(mContext,layoutID,viewGroup);
        return (LvHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewID){

        View view=mViewSparseArray.get(viewID);
        if(view==null){
            view=(View) mConvertView.findViewById(viewID);
            mViewSparseArray.put(viewID,view);
        }

        return (T)view;
    }



    public View getConvertView(){
        return mConvertView;
    }
}
