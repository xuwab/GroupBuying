package com.naf.groupbuying.entity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//import com.example.cniaotuan.R;
//import com.example.cniaotuan.activity.DetailActivity;
//import com.example.cniaotuan.adapter.CommenAdapter;
//import com.example.cniaotuan.adapter.MyGridAdapter;
//import com.example.cniaotuan.adapter.MyPagerAdapter;
//import com.example.cniaotuan.adapter.ViewHolder;
//import com.example.cniaotuan.bean.Config;
//import com.example.cniaotuan.entity.FilmInfo;
//import com.example.cniaotuan.entity.GoodsInfo;
//import com.example.cniaotuan.entity.HomeIconInfo;
//import com.example.cniaotuan.listner.MyPagerListner;
//import com.example.cniaotuan.nohttp.CallServer;
//import com.example.cniaotuan.nohttp.HttpListner;
//import com.example.cniaotuan.widget.ViewPagerIndicator;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.google.gson.Gson;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yolanda.nohttp.NoHttp;
//import com.yolanda.nohttp.RequestMethod;
//import com.yolanda.nohttp.rest.Request;
//import com.yolanda.nohttp.rest.Response;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment {

//    //广告条的数据
//    private List<View> mViewsAdver = new ArrayList<>();
//
//    private List<View> mViews = new ArrayList<>();
//    /**
//     * gridView两页的数据
//     */
//    private List<HomeIconInfo> mPagerOneData = new ArrayList<>();
//    private List<HomeIconInfo> mPagerTwoData = new ArrayList<>();
//    private ViewPagerIndicator mIndicator;
//    private View mInflate;
//
//    /**
//     * 自定义的商品存放容器
//     **/
//    private List<GoodsInfo.ResultBean.GoodlistBean> mDatalist = new ArrayList<>();
//    /**
//     * 自定义一个存放图片的容器
//     **/
//    private int[] resID = new int[]{R.mipmap.a01, R.mipmap.a01, R.mipmap.a01, R.mipmap.a01};
//
//
//    private MyAdapter mMyAdapter;
//    private ViewPager mPagerAdaver;
//    private ViewPager mViewPager;
//    private ViewPagerIndicator mIndicatorAdver;
//
//    private LinearLayout mLayoutFilm;
//    private ListView mListView;
//    private PullToRefreshListView mPullResfreshView;
//
//    public MainFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //防止重新加载数据
//        if (mInflate == null) {
//            mInflate = inflater.inflate(R.layout.fragment_home, container, false);
//            ButterKnife.inject(this, mInflate);
//            mPullResfreshView = (PullToRefreshListView) mInflate.findViewById(R.id.listView);
//            initData();
//            initView();
//        }
//        return mInflate;
//    }
//
//
//    /***
//     * 初始化gridview的数据
//     */
//    private void initData() {
//
//        //获取资源文件的数据
//        String[] iconName = getResources().getStringArray(R.array.home_bar_labels);
//        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_bar_icon);
//        //初始化商品分类的数据
//        for (int i = 0; i < iconName.length; i++) {
//            if (i < 8) {
//                mPagerOneData.add(new HomeIconInfo(iconName[i], typedArray.getResourceId(i, 0)));
//            } else {
//                mPagerTwoData.add(new HomeIconInfo(iconName[i], typedArray.getResourceId(i, 0)));
//            }
//        }
//
//        //初始化广告条的数据
//        for (int i = 0; i < 4; i++) {
//            View viewAdver = View.inflate(getActivity(), R.layout.pager_image_item, null);
//            ImageView ivItem = (ImageView) viewAdver.findViewById(R.id.iv_item);
//            ivItem.setImageResource(resID[i]);
//            mViewsAdver.add(viewAdver);
//        }
//
//
//        /**猜你喜欢的请求**/
//        Request<String> recommendRequest = NoHttp.createStringRequest(spRecommendURL_NEW, RequestMethod.GET);
//        CallServer.getInstance().add(getActivity(), 0, recommendRequest, this, true, true);
//
//        /**热门电影的请求**/
//        Request<String> filmRequest = NoHttp.createStringRequest(filmHotUrl, RequestMethod.GET);
//        CallServer.getInstance().add(getActivity(), 1, filmRequest, this, true, true);
//    }
//
//    /***
//     * 初始化gridview
//     */
//    private void initView() {
//
//        mPullResfreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            //下拉刷新
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                new MyAsync().execute();
//            }
//        });
//
//
//        //listview的头部
//        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_headviewall, null);
//        //广告条viewpager和小标点
//        mPagerAdaver = (ViewPager) headView.findViewById(R.id.pager_adver);
//        mIndicatorAdver = (ViewPagerIndicator) headView.findViewById(R.id.indicator_adver);
//        mPagerAdaver.setOnPageChangeListener(new MyPagerListner(mIndicatorAdver));
//        mPagerAdaver.setAdapter(new MyPagerAdapter(mViewsAdver));
//
//        //商品分类viewpager和下标点
//        mViewPager = (ViewPager) headView.findViewById(R.id.viewPager);
//        mIndicator = (ViewPagerIndicator) headView.findViewById(R.id.indicator);
//        mViewPager.setOnPageChangeListener(new MyPagerListner(mIndicator));
//
//        //电影列表
//        mLayoutFilm = (LinearLayout) headView.findViewById(R.id.layout_film);
//
//        //第一页数据
//        View pagerOne = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
//        GridView gridView01 = (GridView) pagerOne.findViewById(R.id.gridView);
//        gridView01.setAdapter(new MyGridAdapter(mPagerOneData, getActivity()));
//        gridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//        //第二页数据
//        View pagerTwo = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
//        GridView gridView02 = (GridView) pagerTwo.findViewById(R.id.gridView);
//        gridView02.setAdapter(new MyGridAdapter(mPagerTwoData, getActivity()));
//        gridView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//        //添加到ViewPager中
//        mViews.add(pagerOne);
//        mViews.add(pagerTwo);
//        mViewPager.setAdapter(new MyPagerAdapter(mViews));
//        //添加listview的头部
//        mListView = mPullResfreshView.getRefreshableView();
//        mListView.addHeaderView(headView);
//        mMyAdapter = new MyAdapter(mDatalist);
//
//        mListView.setAdapter(mMyAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String goods_id = mDatalist.get(i - 2).getGoods_id();
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                intent.putExtra("goods_id", goods_id);
//                startActivity(intent);
//            }
//        });
//    }
//
//    class MyAdapter extends CommenAdapter {
//
//        public MyAdapter(List data) {
//            super(data);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup viewGroup) {
//            ViewHolder holder = ViewHolder.get(getActivity(), R.layout.goods_list_item, convertView, viewGroup);
//            TextView tvTitle = holder.getView(R.id.title);
//            TextView tvContent = holder.getView(R.id.tv_content);
//            TextView tvPrice = holder.getView(R.id.price);
//            TextView tvValue = holder.getView(R.id.value);
//            TextView tvBought = holder.getView(R.id.count);
//
//
//            tvTitle.setText(mDatalist.get(position).getProduct());
//            tvContent.setText(mDatalist.get(position).getTitle());
//            tvPrice.setText(mDatalist.get(position).getPrice());
//            tvValue.setText(mDatalist.get(position).getValue());
//            tvValue.setPaintFlags(tvValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            tvBought.setText("已售:" + mDatalist.get(position).getBought());
//
//            Uri uri = Uri.parse(mDatalist.get(position).getImages().get(0).getImage());
//            SimpleDraweeView draweeView = holder.getView(R.id.iv_icon2);
//            draweeView.setImageURI(uri);
//
//            return holder.getConvertView();
//        }
//    }
//
//
//    @Override
//    public void onSucceed(int what, Response<String> response) {
//        Gson gson = new Gson();
//        switch (what) {
//            case 0:
//                GoodsInfo goodsInfo = gson.fromJson(response.get(), GoodsInfo.class);
//                List<GoodsInfo.ResultBean.GoodlistBean> goodlist =
//                        goodsInfo.getResult().getGoodlist();
//                mDatalist.addAll(goodlist);
//                mMyAdapter.notifyDataSetChanged();
//                break;
//
//            case 1:
//                FilmInfo filmInfo = gson.fromJson(response.get(), FilmInfo.class);
//                List<FilmInfo.ResultBean> result = filmInfo.getResult();
//                for (int i = 0; i < result.size(); i++) {
//                    FilmInfo.ResultBean resultBean = result.get(i);
//                    View filmView = View.inflate(getActivity(), R.layout.film_item, null);
//                    SimpleDraweeView ivFilmIcon = (SimpleDraweeView) filmView.findViewById(R.id.iv_filmIcon);
//                    Uri uri = Uri.parse(resultBean.getImageUrl());
//                    ivFilmIcon.setImageURI(uri);
//                    TextView tvFilmName = (TextView) filmView.findViewById(R.id.tv_filmName);
//                    TextView tvFilmCount = (TextView) filmView.findViewById(R.id.tv_film_count);
//                    tvFilmName.setText(resultBean.getFilmName());
//                    tvFilmCount.setText(resultBean.getGrade() + "分");
//                    mLayoutFilm.addView(filmView);
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.reset(this);
//    }
//
//
//    class MyAsync extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//                //这里可以进行网络数据的操作
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (mPullResfreshView != null) {
//                mPullResfreshView.onRefreshComplete();
//            }else{
//
//            }
//        }
//    }


}
