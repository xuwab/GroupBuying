package com.naf.groupbuying.test;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.naf.groupbuying.R;
import com.naf.groupbuying.activity.MainActivity;

/**
 * Created by naf on 2016/11/16.
 */

public class ToolbarHelper {
    private Context mContext;
    private FrameLayout mFrameLayout;
    private LayoutInflater mLayoutInflater;
    private Toolbar mToolbar;
    private View mUserView;

    public ToolbarHelper(Context context,int layoutID){
        this.mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        /**初始化整个内容**/
        initContentView();

        /**初始化用户自定义的布局**/
        initUserView(layoutID);

        /**初始化toolbar**/
        initToolbar();
    }

    private void initUserView(int layoutID) {
        mUserView=mLayoutInflater.inflate(layoutID,null);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mUserView.setLayoutParams(layoutParams);
    }

    private void initToolbar() {
        View view=mLayoutInflater.inflate(R.layout.activity_toolbar, (ViewGroup) mUserView);
        mToolbar= (Toolbar) view.findViewById(R.id.toolbar);
    }

    private void initContentView() {
        mFrameLayout=new FrameLayout(mContext);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.setLayoutParams(layoutParams);
    }

    public Toolbar getToolbar(){

        return mToolbar;
    }
    public FrameLayout getmContentView(){
        return mFrameLayout;
    }

    public ViewGroup getUserView(){
        return (ViewGroup) mUserView;
    }
}



