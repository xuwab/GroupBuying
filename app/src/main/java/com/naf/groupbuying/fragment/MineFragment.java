package com.naf.groupbuying.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naf.groupbuying.R;
import com.naf.groupbuying.activity.Collection.CollectionActivity;
import com.naf.groupbuying.activity.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/22.
 */

public class MineFragment extends Fragment {

    private static final String TAG = "xuwab";
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rl_logined)
    RelativeLayout mRlLogined;
    @BindView(R.id.rl_unlogin)
    RelativeLayout mRlUnlogin;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, mView);

        return mView;
    }

    @OnClick({R.id.btn_login,R.id.tv_collect})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.tv_collect:
                startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(Boolean islogin) {
        mRlLogined.setVisibility(View.VISIBLE);
        mRlUnlogin.setVisibility(View.GONE);
        mTvName.setText((String) BmobUser.getObjectByKey("username"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
