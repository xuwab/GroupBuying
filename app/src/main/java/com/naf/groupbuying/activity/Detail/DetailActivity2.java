package com.naf.groupbuying.activity.Detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.naf.groupbuying.R;
import com.naf.groupbuying.adapter.MyPagerAdapter;
import com.naf.groupbuying.bean.ContantsPool;
import com.naf.groupbuying.custom.MyScrollView;
import com.naf.groupbuying.entity.DetailInfo;
import com.naf.groupbuying.entity.FavorInfo;
import com.naf.groupbuying.listner.Bmob.BmobDeleteListener;
import com.naf.groupbuying.listner.Bmob.BmobInsertListener;
import com.naf.groupbuying.listner.Bmob.BmobQueryListener;
import com.naf.groupbuying.listner.main.MyPagerListner;
import com.naf.groupbuying.nohttp.CallServer;
import com.naf.groupbuying.nohttp.HttpListner;
import com.naf.groupbuying.utils.BmobUtils;
import com.naf.groupbuying.widget.ViewPagerIndicator;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naf on 2016/11/26.
 */

public class DetailActivity2 extends AppCompatActivity implements HttpListner<String>,MyScrollView.ScrollViewListener {
    private static final int REQUEST_DETAIL = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_decs)
    TextView tvDecs;
    @BindView(R.id.tv_bought)
    TextView tvBought;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.web_detail)
    WebView webDetail;
    @BindView(R.id.web_notice)
    WebView webNotice;
    @BindView(R.id.list_recommend)
    ListView listRecommend;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_favor)
    ImageView ivFavor;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.layout_buy)
    RelativeLayout layoutBuy;
    @BindView(R.id.vp_detail)
    ViewPager vpDetail;
    @BindView(R.id.detail_vp_indicator)
    ViewPagerIndicator detailVpIndicator;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;

    private DetailInfo mDetailInfo;
    private List<View> vpList = new ArrayList<>();
    private int mIvDetailHeight;

    private boolean isFavor;
    private String mGoodId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mGoodId = intent.getStringExtra("good_id");

        initListener();


        Request<String> request = NoHttp.createStringRequest(ContantsPool.baseUrl + mGoodId + ".txt", RequestMethod.GET);
        CallServer.getInstance().add(DetailActivity2.this, REQUEST_DETAIL, request, this, true, true);
        initFavor();

    }

    private void initFavor() {
        BmobUtils.getInstance(new BmobQueryListener() {

            @Override
            public void querySuccess() {
                isFavor=true;
                ivFavor.setImageResource(R.mipmap.icon_collected_black);
                Toast.makeText(DetailActivity2.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void queryError() {
                isFavor=false;
                ivFavor.setImageResource(R.mipmap.icon_uncollected);
                Toast.makeText(DetailActivity2.this, "error", Toast.LENGTH_SHORT).show();
            }
        }).queryFavor("goodId",mGoodId);
    }

    private void initListener() {
        //但是需要注意的是OnGlobalLayoutListener可能会被多次触发，因此在得到了高度之后，要将OnGlobalLayoutListener注销掉。
        final ViewTreeObserver vto=vpDetail.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvDetailHeight=vpDetail.getHeight();
                scrollView.setScrollViewListener(DetailActivity2.this);
                vpDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_favor, R.id.iv_share, R.id.btn_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_favor:
                List<String> list=new ArrayList<>();
                list.add(mGoodId);
                if(!isFavor){
                    BmobUtils.getInstance(new BmobInsertListener() {
                        @Override
                        public void addFavorSuccess() {
                            Toast.makeText(DetailActivity2.this, "success", Toast.LENGTH_SHORT).show();
                            ivFavor.setImageResource(R.mipmap.icon_collected_black);
                            isFavor=true;
                        }

                        @Override
                        public void addFavorError() {
                            Toast.makeText(DetailActivity2.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }).insertFavor(mDetailInfo);
                }else {
                    BmobUtils.getInstance(new BmobDeleteListener() {
                        @Override
                        public void deleteSuccess() {
                            Toast.makeText(DetailActivity2.this, "Success", Toast.LENGTH_SHORT).show();
                            ivFavor.setImageResource(R.mipmap.icon_uncollected);
                            isFavor=false;
                        }

                        @Override
                        public void deleteError() {
                            Toast.makeText(DetailActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }).deleteFavor("goodId",list);
                }

                break;
            case R.id.iv_share:
                break;
            case R.id.btn_buy:
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case REQUEST_DETAIL:
                Gson gson = new Gson();
                mDetailInfo = gson.fromJson(response.get(), DetailInfo.class);
                //本单详情的网页信息
                String details = mDetailInfo.getResult().getDetails();
                //温馨提示
                String notice = mDetailInfo.getResult().getNotice();

                webDetail.loadDataWithBaseURL("about:blank", details, "text/html", "UTF-8", null);
                webNotice.loadDataWithBaseURL("about:blank", notice, "text/html", "UTF-8", null);
                //标题
                tvTitle.setText(mDetailInfo.getResult().getProduct());
                //描述
                tvDecs.setText(mDetailInfo.getResult().getTitle());
                //已售
                tvBought.setText(mDetailInfo.getResult().getValue());

                tvPrice.setText(mDetailInfo.getResult().getPrice());

                tvValue.setText(mDetailInfo.getResult().getValue());
                List<String> list = mDetailInfo.getResult().getDetail_imags();
                for (int i = 0; i < 4; i++) {
                    View view = getLayoutInflater().inflate(R.layout.detail_vp_item, null);
                    SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.sv_detail);
                    draweeView.setImageURI(list.get(i));
                    vpList.add(view);
                }
                vpDetail.setAdapter(new MyPagerAdapter(vpList));
                detailVpIndicator.setNumbers(4);
                vpDetail.setOnPageChangeListener(new MyPagerListner(detailVpIndicator));

                break;
        }

    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

    @Override
    public void scrollChanged(int x, int y, int oldl, int oldt) {
        if(y<=0){
            tvTitlebar.setVisibility(View.GONE);
            layoutTitle.setBackgroundColor(Color.argb(0,0,0,0));
        }else if(y>0&&y<=mIvDetailHeight){
            float scale=(float) y/mIvDetailHeight;
            float alpha=255*scale;
            tvTitlebar.setVisibility(View.VISIBLE);
            tvTitlebar.setText(mDetailInfo.getResult().getProduct());
            layoutTitle.setBackgroundColor(Color.argb((int)alpha,255,255,255));
        }else {
            tvTitlebar.setVisibility(View.VISIBLE);
            tvTitlebar.setText(mDetailInfo.getResult().getProduct());
            layoutTitle.setBackgroundColor(Color.argb(255,255,255,255));
        }
    }
}
