package com.naf.groupbuying.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.naf.groupbuying.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by naf on 2016/11/10.
 */

public class GuideActivity extends AppCompatActivity {

    int[] images = new int[]{R.mipmap.guide_1,
            R.mipmap.guide_2,
            R.mipmap.guide_3,
            R.mipmap.guide_4};
    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.ib_guide)
    ImageButton ibGuide;
    private ArrayList<View> mImageViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
        initView();
        setAction();
    }

    private void setAction() {
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViews.size() - 1) {
                    ibGuide.setVisibility(View.VISIBLE);
                }else {
                    ibGuide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ibGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        vpGuide.setAdapter(new PagerItemAdater());
    }

    private void initData() {
        mImageViews = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_pager_guide, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_pager);
            imageView.setBackgroundResource(images[i]);
            mImageViews.add(view);
        }
    }


    class PagerItemAdater extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mImageViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
