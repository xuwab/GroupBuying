package com.naf.groupbuying.activity.Detail;

//import android.content.Intent;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.webkit.WebView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.cniaotuan.R;
//import com.example.cniaotuan.bean.Config;
//import com.example.cniaotuan.entity.DetailInfo;
//import com.example.cniaotuan.entity.FavorInfo;
//import com.example.cniaotuan.listner.IBmobQueryListener;
//import com.example.cniaotuan.nohttp.CallServer;
//import com.example.cniaotuan.nohttp.HttpListner;
//import com.example.cniaotuan.utils.BmobUtils;
//import com.example.cniaotuan.widget.ObservableScrollView;
//import com.google.gson.Gson;
//import com.yolanda.nohttp.NoHttp;
//import com.yolanda.nohttp.RequestMethod;
//import com.yolanda.nohttp.rest.Request;
//import com.yolanda.nohttp.rest.Response;
//
//import java.util.List;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//import butterknife.OnClick;
//import cn.bmob.v3.exception.BmobException;

public class DetailActivity{
//public class DetailActivity extends AppCompatActivity implements HttpListner<String>, ObservableScrollView.ScrollViewListener {

//    @InjectView(R.id.iv_detail)
//    ImageView mIvDetail;
//    @InjectView(R.id.tv_title)
//    TextView mTvTitle;
//    @InjectView(R.id.tv_decs)
//    TextView mTvDecs;
//    @InjectView(R.id.textView)
//    TextView mTextView;
//    @InjectView(R.id.tv_bought)
//    TextView mTvBought;
//    @InjectView(R.id.tv_title2)
//    TextView mTvTitle2;
//    @InjectView(R.id.tv_address)
//    TextView mTvAddress;
//    @InjectView(R.id.tv_time)
//    TextView mTvTime;
//    @InjectView(R.id.web_detail)
//    WebView mWebDetail;
//    @InjectView(R.id.web_notice)
//    WebView mWebNotice;
//    @InjectView(R.id.list_recommend)
//    ListView mListRecommend;
//    @InjectView(R.id.iv_back)
//    ImageView mIvBack;
//    @InjectView(R.id.iv_favor)
//    ImageView mIvFavor;
//    @InjectView(R.id.iv_share)
//    ImageView mIvShare;
//    @InjectView(R.id.tv_price)
//    TextView mTvPrice;
//    @InjectView(R.id.tv_value)
//    TextView mTvValue;
//    @InjectView(R.id.btn_buy)
//    Button mBtnBuy;
//
//    @InjectView(R.id.layout_title)
//    RelativeLayout mLayoutTitle;
//    @InjectView(R.id.layout_buy)
//    RelativeLayout mLayoutBuy;
//
//    //titlebar的标题
//    @InjectView(R.id.tv_titlebar)
//    TextView mTvTitlebar;
//    //自定义的scrollView
//    @InjectView(R.id.scrollView)
//    ObservableScrollView mScrollView;
//
//    private DetailInfo mDetailInfo;
//    private int mImageHeight;
//    private boolean isFavor;
//    private String mGoods_id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//        ButterKnife.inject(this);
//        Intent intent = getIntent();
//        mGoods_id = intent.getStringExtra("goods_id");
//
//        /**商品详情的请求**/
//        Request<String> recommendRequest = NoHttp.createStringRequest(Config.baseUrl + mGoods_id + ".txt", RequestMethod.GET);
//        CallServer.getInstance().add(this, 0, recommendRequest, this, true, true);
//
//        initData();
//        initListener();
//
//    }
//
//    /***
//     * 初始化数据
//     */
//    private void initData() {
//        BmobUtils.getInstance(new IBmobQueryListener() {
//            @Override
//            public void querySucess(List<FavorInfo> object) {
//                boolean favor = object.get(0).isFavor();
//                mIvFavor.setImageResource(favor ? R.drawable.icon_collected_black : R.drawable.icon_uncollect_black);
//                isFavor = favor?true:false;
//            }
//
//            @Override
//            public void queryFailure(BmobException e) {
//
//            }
//        }).queryData(new FavorInfo(), "goods_id", mGoods_id);
//    }
//
//    /***
//     * 获取顶部的图片高度,设置滚动监听
//     */
//    private void initListener() {
//        ViewTreeObserver vto = mIvDetail.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mIvDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//                mImageHeight = mIvDetail.getHeight();
//
//                mScrollView.setScrollViewListener(DetailActivity.this);
//            }
//        });
//    }
//
//    @Override
//    public void onSucceed(int what, Response<String> response) {
//        switch (what) {
//            case 0:
//                Gson gson = new Gson();
//                mDetailInfo = gson.fromJson(response.get(), DetailInfo.class);
//                //本单详情的网页信息
//                String details = mDetailInfo.getResult().getDetails();
//                //温馨提示
//                String notice = mDetailInfo.getResult().getNotice();
//
//                mWebDetail.loadDataWithBaseURL("about:blank", details, "text/html", "UTF-8", null);
//                mWebNotice.loadDataWithBaseURL("about:blank", notice, "text/html", "UTF-8", null);
//                //标题
//                mTvTitle.setText(mDetailInfo.getResult().getProduct());
//                //描述
//                mTvDecs.setText(mDetailInfo.getResult().getTitle());
//                //已售
//                mTvBought.setText(mDetailInfo.getResult().getValue());
//                //详情界面的图片
//                Uri uri = Uri.parse(mDetailInfo.getResult().getImages().get(0).getImage());
//                mIvDetail.setImageURI(uri);
//
//                break;
//        }
//    }
//
//    @Override
//    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//
//    }
//
//    @OnClick({R.id.iv_detail, R.id.iv_back, R.id.iv_favor, R.id.iv_share, R.id.btn_buy})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_detail:
//
//                Intent intent = new Intent(DetailActivity.this, ImageGalleryActivity.class);
//                intent.putExtra("detailInfo", mDetailInfo);
//                startActivity(intent);
//
//                break;
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.iv_favor:
//                FavorInfo favorInfo = new FavorInfo();
//                favorInfo.setGoods_id(mDetailInfo.getResult().getGoods_id());
//                favorInfo.setFavor(true);
//                favorInfo.setPruduct(mDetailInfo.getResult().getProduct());
//                favorInfo.setPrice(mDetailInfo.getResult().getPrice());
//                favorInfo.setValue(mDetailInfo.getResult().getValue());
//                favorInfo.setImageUrl(mDetailInfo.getResult().getImages().get(0).getImage());
//                if (!isFavor) {
//                    Toast.makeText(DetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
//                    mIvFavor.setImageResource(R.drawable.icon_collected_black);
//                    BmobUtils.insertData(favorInfo);
//                    isFavor = true;
//                } else {
//                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
//                    mIvFavor.setImageResource(R.drawable.icon_uncollect_black);
//                    BmobUtils.deleteData(favorInfo);
//                    isFavor = false;
//                }
//
//                break;
//            case R.id.iv_share:
//
//                break;
//            case R.id.btn_buy:
//
//                break;
//        }
//    }
//
//
//    @Override
//    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//        if (y <= 0) {
//            mTvTitlebar.setVisibility(View.GONE);
//            mLayoutTitle.setBackgroundColor(Color.argb(0, 0, 0, 0));
//        } else if (y > 0 && y <= mImageHeight) {
//            float scale = (float) y / mImageHeight;
//            float alpha = (255 * scale);
//            mTvTitlebar.setVisibility(View.VISIBLE);
//            mTvTitlebar.setText(mDetailInfo.getResult().getProduct());
//            mTvTitlebar.setTextColor(Color.argb((int) alpha, 0, 0, 0));
//            mLayoutTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//        } else {
//            mTvTitlebar.setVisibility(View.VISIBLE);
//            mTvTitlebar.setText(mDetailInfo.getResult().getProduct());
//            mTvTitlebar.setTextColor(Color.argb(0, 0, 0, 0));
//            mLayoutTitle.setBackgroundColor(Color.argb(255, 255, 255, 255));
//        }
//    }
}
