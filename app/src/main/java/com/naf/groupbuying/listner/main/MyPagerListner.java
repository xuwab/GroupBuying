package com.naf.groupbuying.listner.main;

import android.support.v4.view.ViewPager;

import com.naf.groupbuying.widget.ViewPagerIndicator;


//ViewPager的监听事件
    public class MyPagerListner implements ViewPager.OnPageChangeListener {
    private ViewPagerIndicator mIndicator;

    //自动播放数目
    private int autoNum;

    public MyPagerListner(ViewPagerIndicator indicator) {
        mIndicator = indicator;
    }

    public MyPagerListner(ViewPagerIndicator indicator,int autoNum) {
        this.mIndicator = indicator;
        this.autoNum=autoNum;
    }

    @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(autoNum!=0){
                position=position%autoNum;
            }
            mIndicator.setOffX(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
