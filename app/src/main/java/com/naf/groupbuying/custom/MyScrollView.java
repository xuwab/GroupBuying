package com.naf.groupbuying.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by naf on 2016/11/27.
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ScrollViewListener mScrollViewListener;
    public interface ScrollViewListener{
        void scrollChanged(int l, int t, int oldl, int oldt);
    }
    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.mScrollViewListener=scrollViewListener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mScrollViewListener!=null){
            mScrollViewListener.scrollChanged(l, t, oldl, oldt);
        }
    }
}
