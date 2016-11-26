package com.naf.groupbuying.activity.Detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.naf.groupbuying.R;
import com.naf.groupbuying.bean.ContantsPool;
import com.naf.groupbuying.entity.DetailInfo;
import com.naf.groupbuying.nohttp.CallServer;
import com.naf.groupbuying.nohttp.HttpListner;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naf on 2016/11/26.
 */

public class DetailActivity2 extends AppCompatActivity implements HttpListner<String>{
    private static final int REQUEST_DETAIL = 1;
    @BindView(R.id.iv_detail)
    SimpleDraweeView ivDetail;
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
    @BindView(R.id.scrollView)
    ScrollView scrollView;
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

    private DetailInfo mDetailInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        String goodId=intent.getStringExtra("good_id");

        Request<String> request= NoHttp.createStringRequest(ContantsPool.baseUrl+goodId+".txt", RequestMethod.GET);
        CallServer.getInstance().add(DetailActivity2.this,REQUEST_DETAIL,request,this,true,true);

    }

    @OnClick({R.id.iv_back, R.id.iv_favor, R.id.iv_share, R.id.btn_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_favor:
                break;
            case R.id.iv_share:
                break;
            case R.id.btn_buy:
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what){
            case REQUEST_DETAIL:
                Gson gson=new Gson();
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
                //详情界面的图片
                Uri uri = Uri.parse(mDetailInfo.getResult().getImages().get(0).getImage());
                ivDetail.setImageURI(uri);
                break;
        }

    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}
