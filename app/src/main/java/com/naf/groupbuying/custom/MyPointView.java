package com.naf.groupbuying.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.naf.groupbuying.R;

/**
 * Created by naf on 2016/11/13.
 */


public class MyPointView extends View {

    private Paint mPaintbg;
    private Paint mPaintfore;
    private float offset;
    private int foreColor;
    private int num;

    public MyPointView(Context context) {
        super(context);
        initPaint();
    }



    public MyPointView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.MyPointView);
        num=typedArray.getInteger(R.styleable.MyPointView_MyPointView_Num,4);

        foreColor=typedArray.getColor(R.styleable.MyPointView_MyPointView_foreColor,Color.BLUE);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<num;i++){
            canvas.drawCircle(20+i*20*3,20,20,mPaintbg);
        }
        canvas.drawCircle(offset,20,20,mPaintfore);
    }


    private void initPaint() {
        mPaintbg = new Paint();
        mPaintbg.setStrokeWidth(1);
        mPaintbg.setStyle(Paint.Style.STROKE);
        mPaintbg.setColor(Color.YELLOW);

        mPaintfore = new Paint();
        mPaintfore.setColor(foreColor);
        mPaintfore.setStyle(Paint.Style.FILL);
    }

    public void setOffset(int position, float positionOffset){
        position %=num;
        offset=20+position*3*20+positionOffset*20*3;
        if(position==num-1&&offset>20+position*3*20)
            return;

        invalidate();
    }
}
