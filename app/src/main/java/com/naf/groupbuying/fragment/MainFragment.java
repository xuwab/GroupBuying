package com.naf.groupbuying.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.naf.groupbuying.R;
import com.naf.groupbuying.adapter.LvCommonAdapter;
import com.naf.groupbuying.adapter.main.FavouriteAdapter;
import com.naf.groupbuying.adapter.main.GuideGridAdapter;
import com.naf.groupbuying.adapter.MyPagerAdapter;
import com.naf.groupbuying.bean.ContantsPool;
import com.naf.groupbuying.entity.GoodsInfo;
import com.naf.groupbuying.entity.HomeIconInfo;
import com.naf.groupbuying.listner.MyPagerListner;
import com.naf.groupbuying.nohttp.CallServer;
import com.naf.groupbuying.nohttp.HttpListner;
import com.naf.groupbuying.widget.ViewPagerIndicator;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ContantsPool,HttpListner<String>{
    private static final int REQUEST_GOODSINFO = 0;
    private View view;

    private View headView;
    private List<View> mViews = new ArrayList<>();

    @BindView(R.id.lv_favourite)
    ListView mListview;
    /**
     * gridView两页的数据
     */
    private List<HomeIconInfo> mPagerOneData = new ArrayList<>();
    private List<HomeIconInfo> mPagerTwoData = new ArrayList<>();

    /**自定义的商品存放容器**/
    private List<GoodsInfo.ResultBean.GoodlistBean> mDatalists = new ArrayList<>();
    private FavouriteAdapter favouriteAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            initData();
            initView();
        }

        return view;
    }

    private void initView() {
        //初始化导航栏Viewpager
        headView=LayoutInflater.from(getActivity()).inflate(R.layout.home_headviewall,null);
        ViewPagerIndicator mIndicator = (ViewPagerIndicator) headView.findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) headView.findViewById(R.id.main_head_viewPager);
        viewPager.setOnPageChangeListener(new MyPagerListner(mIndicator));

        //第一页数据
        View pagerOne = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView gridView01 = (GridView) pagerOne.findViewById(R.id.gridView);
        gridView01.setAdapter(new GuideGridAdapter(mPagerOneData, getActivity()));
        gridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        //第二页数据
        View pagerTwo = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView gridView02 = (GridView) pagerTwo.findViewById(R.id.gridView);
        gridView02.setAdapter(new GuideGridAdapter(mPagerTwoData, getActivity()));
        gridView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        //view添加到viewpager里
        mViews.add(pagerOne);
        mViews.add(pagerTwo);
        viewPager.setAdapter(new MyPagerAdapter(mViews));

        //把导航view添加到listview的headview
        mListview.addHeaderView(headView);
        mListview.setAdapter(favouriteAdapter=new FavouriteAdapter(getActivity(),mDatalists));
    }

    private void initData() {
        //获取资源文件的数据
        String[] iconName = getResources().getStringArray(R.array.home_bar_labels);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_bar_icon);

        for (int i = 0; i < iconName.length; i++) {
            if (i < 8) {
                mPagerOneData.add(new HomeIconInfo(iconName[i], typedArray.getResourceId(i, 0)));
            } else {
                mPagerTwoData.add(new HomeIconInfo(iconName[i], typedArray.getResourceId(i, 0)));
            }
        }

        Request<String> request= NoHttp.createStringRequest(spRecommendURL, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(),REQUEST_GOODSINFO,request,this,true,true);

    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what){
            case REQUEST_GOODSINFO:
                Gson gson=new Gson();
                GoodsInfo goodsInfo=gson.fromJson(response.get(),GoodsInfo.class);
                List<GoodsInfo.ResultBean.GoodlistBean> lists=goodsInfo.getResult().getGoodlist();

                mDatalists.clear();
                mDatalists.addAll(lists);
                favouriteAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}
