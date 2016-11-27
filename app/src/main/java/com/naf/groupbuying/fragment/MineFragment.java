package com.naf.groupbuying.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naf.groupbuying.R;
import com.naf.groupbuying.activity.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/22.
 */

public class MineFragment extends Fragment {

    @BindView(R.id.btn_login)
    Button btnLogin;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
