package com.naf.groupbuying.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.naf.groupbuying.R;
import com.naf.groupbuying.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by Administrator on 2016/11/22.
 */

public class MoreFragment extends Fragment {
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        tvCacheSize.setText(DataCleanManager.getTotalCacheSize(getActivity()));
    }

    @OnClick({R.id.feedback_layout, R.id.kefu_layout,R.id.good_comment_layout,R.id.rl_softvare_update
    ,R.id.clear_cache_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_layout:
                break;
            case R.id.kefu_layout:
                //客服联系
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"18826251809"));
                startActivity(intent);
                break;
            case R.id.good_comment_layout:
//                应用商城评分
                openApplicationMarket(getActivity().getPackageName());
                break;
            case R.id.rl_softvare_update:
//                版本更新
                BmobUpdateAgent.update(getActivity());
                break;
            case R.id.clear_cache_layout:
                DataCleanManager.clearAllCache(getActivity());
                tvCacheSize.setText(DataCleanManager.getTotalCacheSize(getActivity()));
                break;
        }
    }

    private void openApplicationMarket(String packageName) {
        try {
            String url="market://detail?id="+packageName;
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "打开应用市场失败！", Toast.LENGTH_SHORT).show();
            String url="";
            //调用浏览器进入商城
            openLinkByUrl(url);
        }

    }

    private void openLinkByUrl(String url) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
