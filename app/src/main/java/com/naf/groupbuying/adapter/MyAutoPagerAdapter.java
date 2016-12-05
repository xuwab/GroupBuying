package com.naf.groupbuying.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naf on 2016/12/5.
 */

public class MyAutoPagerAdapter extends PagerAdapter {

    private List<View> mViews = new ArrayList<>();

    public MyAutoPagerAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        int index=position%mViews.size();
        View view = mViews.get(index);
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int index=position%mViews.size();
        View view = mViews.get(index);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}