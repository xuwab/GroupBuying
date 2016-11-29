package com.naf.groupbuying.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.naf.groupbuying.R;
import com.naf.groupbuying.entity.LoginInfo;
import com.naf.groupbuying.listner.login.MyEditTextListener;
import com.naf.groupbuying.nohttp.HttpListner;
import com.yolanda.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by naf on 2016/11/27.
 */

public class LoginActivity extends AppCompatActivity implements HttpListner<String> {
    @BindView(R.id.et_quick_phone)
    EditText etQuickPhone;
    @BindView(R.id.et_quick_code)
    EditText etQuickCode;
    @BindView(R.id.quick_login_btn)
    Button quickLoginBtn;
    @BindView(R.id.tv_quick_login)
    TextView mTvQuickLogin;
    @BindView(R.id.tv_count_login)
    TextView mTvCountLogin;
    @BindView(R.id.ll_login)
    LinearLayout mLlLogin;
    @BindView(R.id.ll_quick_login)
    LinearLayout mLlQuickLogin;
    @BindView(R.id.view_line_left)
    View mViewLineLeft;
    @BindView(R.id.view_line_right)
    View mViewLineRight;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;

    private boolean isLoginEnable;

    private String phone;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        etQuickPhone.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
                isLoginEnable = TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) ? false : true;
                quickLoginBtn.setEnabled(isLoginEnable);
            }
        });

        etQuickCode.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                code = s.toString();
                isLoginEnable = TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) ? false : true;
                quickLoginBtn.setEnabled(isLoginEnable);
            }
        });
    }

    @OnClick(R.id.quick_login_btn)
    public void onClick() {
        String account = etQuickPhone.getText().toString();
        String password = etQuickCode.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(account);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case 0:
                Gson gson = new Gson();
                LoginInfo loginInfo = gson.fromJson(response.get(), LoginInfo.class);
                String userName = loginInfo.getUsername();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "登录成功  ", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        switch (what) {
            case 0:
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick({R.id.tv_quick_login, R.id.tv_count_login,R.id.iv_delete_uname, R.id.iv_delete_mobile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quick_login:
                mLlLogin.setVisibility(View.GONE);
                mLlQuickLogin.setVisibility(View.VISIBLE);
                mTvQuickLogin.setTextColor(getResources().getColor(R.color.orange));
                mTvCountLogin.setTextColor(getResources().getColor(R.color.content_color));
                mViewLineLeft.setVisibility(View.VISIBLE);
                mViewLineRight.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_count_login:
                mLlLogin.setVisibility(View.VISIBLE);
                mLlQuickLogin.setVisibility(View.GONE);
                mTvQuickLogin.setTextColor(getResources().getColor(R.color.content_color));
                mTvCountLogin.setTextColor(getResources().getColor(R.color.orange));
                mViewLineLeft.setVisibility(View.INVISIBLE);
                mViewLineRight.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_delete_uname:
                mUsername.setText("");
                mPassword.setText("");
                break;
            case R.id.iv_delete_mobile:
                etQuickPhone.setText("");
                break;
        }
    }
}
