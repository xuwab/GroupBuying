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



//package com.example.indicator;
//
//        import android.content.Context;
//        import android.content.res.TypedArray;
//        import android.graphics.Canvas;
//        import android.graphics.Color;
//        import android.graphics.Paint;
//        import android.util.AttributeSet;
//        import android.view.View;
//
///**
// * Created by hongkl on 16/7/24.
// * 1\没有办法无限滚动
// * 2\自动滚动,当滚动到最后一个点的时候,会滚出范围
// * 3\代码没有进行封装
// *
// * 封装属性
// */
//public class Indicator extends View {
//    /**前景色的圆的画笔**/
//    private Paint mForePaint;
//    /**背景颜色的画笔**/
//    private Paint mBgPaint;
//    /**Indicator数量**/
//    private int mNumber = 4;
//    /**Indicator半径**/
//    private int mRadius = 10;
//    /**Indicator的背景色画笔颜色**/
//    private int mBgColor = Color.RED;
//    /**Indicator的前景色画笔颜色**/
//    private int mForeColor = Color.BLUE;
//
//
//
//    /***
//     * 设置偏移量的方法
//     * @param position
//     * @param positionOffset
//     */
//    public void setOffset(int position,float positionOffset) {
//        position %= mNumber;
//        mOffset = position * 3 * mRadius + positionOffset * 3 * mRadius;
//        //重绘
//        invalidate();
//    }
//
//    /**移动的偏移量**/
//    private float mOffset;
//
//    public Indicator(Context context) {
//        super(context);
//    }
//
//    public Indicator(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initPaint();
//
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
//        mNumber = typedArray.getInteger(R.styleable.Indicator_setNumber, mNumber);
//
//    }
//
//    private void initPaint() {
//        mForePaint = new Paint();
//        mForePaint.setAntiAlias(true);
//        mForePaint.setStyle(Paint.Style.FILL);
//        mForePaint.setColor(mForeColor);
//        mForePaint.setStrokeWidth(2);
//
//        mBgPaint = new Paint();
//        mBgPaint.setAntiAlias(true);
//        mBgPaint.setStyle(Paint.Style.STROKE);
//        mBgPaint.setColor(mBgColor);
//        mBgPaint.setStrokeWidth(2);
//
//    }
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        for (int i = 0; i < mNumber; i++) {
//            canvas.drawCircle(60 + i * mRadius * 3,60,mRadius,mBgPaint);
//        }
//        canvas.drawCircle(60 + mOffset,60,mRadius,mForePaint);
//    }
//}

