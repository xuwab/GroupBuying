package com.naf.groupbuying.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.naf.groupbuying.R;
import com.naf.groupbuying.activity.Detail.DetailActivity;
import com.naf.groupbuying.activity.Detail.DetailActivity2;
import com.naf.groupbuying.adapter.MyAutoPagerAdapter;
import com.naf.groupbuying.adapter.MyPagerAdapter;
import com.naf.groupbuying.adapter.main.FavouriteAdapter;
import com.naf.groupbuying.adapter.main.FilmAdapter;
import com.naf.groupbuying.adapter.main.GuideGridAdapter;
import com.naf.groupbuying.bean.ContantsPool;
import com.naf.groupbuying.entity.FilmInfo;
import com.naf.groupbuying.entity.GoodsInfo;
import com.naf.groupbuying.entity.HomeIconInfo;
import com.naf.groupbuying.listner.main.MyPagerListner;
import com.naf.groupbuying.nohttp.CallServer;
import com.naf.groupbuying.nohttp.HttpListner;
import com.naf.groupbuying.widget.ViewPagerIndicator;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ContantsPool, HttpListner<String> {
    private static final int REQUEST_GOODSINFO = 0;
    private static final int REQUEST_FILM = 1;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;
    private View view;

    private List<View> mViews = new ArrayList<>();

    private ViewPager mPagerAdaver;
    private ViewPagerIndicator mIndicatorAdver;


    @BindView(R.id.lv_favourite)
    ListView mListview;
    /**
     * 广告条的数据
     */
    private List<View> mViewsAdver = new ArrayList<>();
    private Handler adHandler=new Handler();

    /**
     * 电影
     */
    private RecyclerView mLayoutFilm;
    /**
     * 分类导航
     */
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    /**
     * gridView两页的数据
     */
    private List<HomeIconInfo> mPagerOneData = new ArrayList<>();
    private List<HomeIconInfo> mPagerTwoData = new ArrayList<>();

    /**
     * 自定义的商品存放容器
     **/
    private List<GoodsInfo.ResultBean.GoodlistBean> mDatalists = new ArrayList<>();
    private FavouriteAdapter favouriteAdapter;
    private int[] adIvID={R.mipmap.ad1,R.mipmap.ad2,R.mipmap.ad3,R.mipmap.ad4};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            initData();
            initView();
            setListViewAction();
        }

        ButterKnife.bind(this, view);
        return view;
    }


    private void setListViewAction() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String goodId = mDatalists.get(position - 1).getGoods_id();
                Intent intent = new Intent(getActivity(), DetailActivity2.class);
                intent.putExtra("good_id", goodId);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //listview的头部
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.main_list_headviewall, null);


        //广告条viewpager和小标点
        mPagerAdaver = (ViewPager) headView.findViewById(R.id.pager_adver);
        mIndicatorAdver = (ViewPagerIndicator) headView.findViewById(R.id.indicator_adver);
        mPagerAdaver.setOnPageChangeListener(new MyPagerListner(mIndicatorAdver,4));
        mPagerAdaver.setAdapter(new MyAutoPagerAdapter(mViewsAdver));
        adHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int item=mPagerAdaver.getCurrentItem();
                mPagerAdaver.setCurrentItem(item+1);
                adHandler.postDelayed(this,2000);
            }
        },2000);


        //电影列表
        mLayoutFilm = (RecyclerView) headView.findViewById(R.id.rv_film);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutFilm.setLayoutManager(layoutManager);



        //商品分类viewpager和下标点
        mViewPager = (ViewPager) headView.findViewById(R.id.viewPager);
        mIndicator = (ViewPagerIndicator) headView.findViewById(R.id.indicator);
        mViewPager.setOnPageChangeListener(new MyPagerListner(mIndicator));

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
        mViewPager.setAdapter(new MyPagerAdapter(mViews));


        //添加到listview的headview
        mListview.addHeaderView(headView);
        mListview.setAdapter(favouriteAdapter = new FavouriteAdapter(getActivity(), mDatalists));


        //初始化下拉刷新SwipeRefreshLayout
        srlMain.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        srlMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlMain.setRefreshing(true);
                Request<String> request = NoHttp.createStringRequest(spRecommendURL_NEW, RequestMethod.GET);
                CallServer.getInstance().add(getActivity(), REQUEST_GOODSINFO, request, MainFragment.this, true, true);
            }
        });

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String goods_id = mDatalists.get(i - 2).getGoods_id();
                Intent intent = new Intent(getActivity(), DetailActivity2.class);
                intent.putExtra("goods_id", goods_id);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        //初始化广告条的数据
        for (int i = 0; i < 4; i++) {
            View viewAdver = View.inflate(getActivity(), R.layout.pager_image_item, null);
            ImageView ivItem = (ImageView) viewAdver.findViewById(R.id.iv_item);
            ivItem.setImageResource(adIvID[i]);
            mViewsAdver.add(viewAdver);
        }



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
        /**商品列表的请求 **/
        Request<String> request = NoHttp.createStringRequest(spRecommendURL_NEW, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), REQUEST_GOODSINFO, request, this, true, true);

        /**热门电影的请求**/
        Request<String> filmRequest = NoHttp.createStringRequest(filmHotUrl, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), REQUEST_FILM, filmRequest, this, true, true);
    }




    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case REQUEST_GOODSINFO:
                GoodsInfo goodsInfo = gson.fromJson(response.get(), GoodsInfo.class);
                List<GoodsInfo.ResultBean.GoodlistBean> lists = goodsInfo.getResult().getGoodlist();

                mDatalists.clear();
                mDatalists.addAll(lists);
                favouriteAdapter.notifyDataSetChanged();

                srlMain.setRefreshing(false);
                break;

            case REQUEST_FILM:
                FilmInfo filmInfo = gson.fromJson(response.get(), FilmInfo.class);
                List<FilmInfo.ResultBean> results = filmInfo.getResult();
                mLayoutFilm.setAdapter(new FilmAdapter(getActivity(),results));
                break;
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}
