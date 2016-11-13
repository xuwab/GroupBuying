package com.naf.groupbuying.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by naf on 2016/11/13.
 */


public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
