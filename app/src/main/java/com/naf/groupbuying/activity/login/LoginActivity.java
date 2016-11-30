package com.naf.groupbuying.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.quick_login_btn)
    Button quickLoginBtn;
    @BindView(R.id.iv_delete_uname)
    ImageView ivDeleteUname;
    @BindView(R.id.iv_delete_mobile)
    ImageView ivDeleteMobile;
    @BindView(R.id.cb_show_pwd)
    CheckBox cbShowPwd;
    @BindView(R.id.ll_forget_pwd)
    LinearLayout llForgetPwd;

    private boolean isLoginEnable;

    private String phone;
    private String code;
    private String userName;
    private String password;

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
                ivDeleteMobile.setVisibility(TextUtils.isEmpty(phone) ? View.GONE : View.VISIBLE);
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
        mUsername.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                userName = s.toString();
                isLoginEnable = TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) ? false : true;
                loginBtn.setEnabled(isLoginEnable);
                ivDeleteUname.setVisibility(TextUtils.isEmpty(phone) ? View.GONE : View.VISIBLE);
            }
        });
        mPassword.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
                isLoginEnable = TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) ? false : true;
                loginBtn.setEnabled(isLoginEnable);
            }
        });
        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
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

    @OnClick({R.id.tv_quick_login, R.id.tv_count_login, R.id.iv_delete_uname, R.id.iv_delete_mobile
            , R.id.login_btn, R.id.quick_login_btn,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quick_login:
                mLlLogin.setVisibility(View.GONE);
                mLlQuickLogin.setVisibility(View.VISIBLE);
                mTvQuickLogin.setTextColor(getResources().getColor(R.color.orange));
                mTvCountLogin.setTextColor(getResources().getColor(R.color.content_color));
                mViewLineLeft.setVisibility(View.VISIBLE);
                mViewLineRight.setVisibility(View.INVISIBLE);
                quickLoginBtn.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.GONE);
                llForgetPwd.setVisibility(View.GONE);
                break;
            case R.id.tv_count_login:
                mLlLogin.setVisibility(View.VISIBLE);
                mLlQuickLogin.setVisibility(View.GONE);
                mTvQuickLogin.setTextColor(getResources().getColor(R.color.content_color));
                mTvCountLogin.setTextColor(getResources().getColor(R.color.orange));
                mViewLineLeft.setVisibility(View.INVISIBLE);
                mViewLineRight.setVisibility(View.VISIBLE);
                quickLoginBtn.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
                llForgetPwd.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_delete_uname:
                mUsername.setText("");
                mPassword.setText("");
                break;
            case R.id.iv_delete_mobile:
                etQuickPhone.setText("");
                break;
            case R.id.login_btn:
                String account = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                BmobUser user = new BmobUser();
                user.setUsername(account);
                user.setPassword(password);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.quick_login_btn:
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
