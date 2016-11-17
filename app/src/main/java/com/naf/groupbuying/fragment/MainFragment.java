package com.naf.groupbuying.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.naf.groupbuying.R;
import com.naf.groupbuying.custom.MyPointView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @BindView(R.id.vp_Fg1)
    ViewPager vp;
    @BindView(R.id.mpv)
    MyPointView mpv;

    private List<View> mViewList;
    private Handler mHandler;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, view);
        initDate();
        initAction();
        return view;
    }

    private void initAction() {
        vp.setAdapter(new myPagerAdater());
        vp.setOnPageChangeListener(new myPagerChangeListener());
        autoScroll();
    }

    private void autoScroll() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int curPosition = vp.getCurrentItem();
                vp.setCurrentItem(curPosition + 1);
                mHandler.postDelayed(this,1000);
            }
        }, 1000);
    }

    private void initDate() {
        mViewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragement_item_blank, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_fg_item);
            imageView.setImageResource(R.mipmap.ic_launcher);
            mViewList.add(view);
        }

        mHandler = new Handler();
    }


    class myPagerAdater extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            position %=4;
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %=4;
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return 10000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class myPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mpv.setOffset(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}




//
//package com.example.indicator;
//
//        import android.os.Bundle;
//        import android.support.v4.view.PagerAdapter;
//        import android.support.v4.view.ViewPager;
//        import android.support.v7.app.AppCompatActivity;
//        import android.util.Log;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ImageView;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private List<View> mViews = new ArrayList<>();
//    private Indicator mIndicator;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initData();
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
//        viewPager.setAdapter(new MyPagerAdapter());
//        viewPager.setOnPageChangeListener(new MyPagerListner());
//        mIndicator = (Indicator) findViewById(R.id.indicator);
//
//
//
//    }
//
//    private void initData() {
//        for (int i = 0; i < 4; i++) {
//            View inflate = getLayoutInflater().inflate(R.layout.pager_item, null);
//            ImageView imageView = (ImageView) inflate.findViewById(R.id.iv);
//            imageView.setImageResource(R.mipmap.ic_launcher);
//            mViews.add(inflate);
//
//        }
//
//    }
//
//    class MyPagerListner implements ViewPager.OnPageChangeListener{
//
//        //ViewPager的滚动的
//
//        /***
//         *
//         * @param position
//         * @param positionOffset    偏移的百分比
//         * @param positionOffsetPixels    偏移量
//         */
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.e("onPageScrolled", "positionOffset<< "+positionOffset +"  positionOffsetPixels<<<" + positionOffsetPixels);
//
//            mIndicator.setOffset(position,positionOffset);
//        }
//        //ViewPager选中
//        @Override
//        public void onPageSelected(int position) {
//
//        }
//        //ViewPager滑动状态改变
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    }
//
//
//    class MyPagerAdapter extends PagerAdapter{
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
////            super.destroyItem(container, position, object);
//            position %= 4;
//            container.removeView(mViews.get(position));
//
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            position %= 4;
//            View view = mViews.get(position);
//            container.addView(view);
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return Integer.MAX_VALUE;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
//    }
//
//
//
//
//}
