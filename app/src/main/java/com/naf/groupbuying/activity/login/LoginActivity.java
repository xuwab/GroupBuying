package com.naf.groupbuying.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.naf.groupbuying.activity.MainActivity;
import com.naf.groupbuying.activity.register.RegisterActivity;
import com.naf.groupbuying.bean.LoginPost;
import com.naf.groupbuying.bean.Qq.BaseUiListener;
import com.naf.groupbuying.bean.Qq.Util;
import com.naf.groupbuying.bean.weibo.ISinaInfo;
import com.naf.groupbuying.bean.weibo.WeiboUtil;
import com.naf.groupbuying.entity.LoginInfo;
import com.naf.groupbuying.listner.login.MyEditTextListener;
import com.naf.groupbuying.nohttp.HttpListner;
import com.sina.weibo.sdk.openapi.models.User;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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

    private boolean isLogin;
    private static boolean isServerSideLogin = false;
    private static Tencent mTencent;
    private UserInfo mInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
        initWeibo();
        initQq();
        initListener();

    }

    private void initWeibo() {
        WeiboUtil.initWeibo(LoginActivity.this);
    }

    private void initQq(){
        mTencent = Tencent.createInstance("1105878246", this.getApplicationContext());
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
                ivDeleteUname.setVisibility(TextUtils.isEmpty(userName) ? View.GONE : View.VISIBLE);
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
                    mPassword.setSelection(mPassword.getText().toString().length());
                } else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPassword.setSelection(mPassword.getText().toString().length());
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
                    EventBus.getDefault().post(new LoginPost(isLogin,""));
                    finish();
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
            , R.id.login_btn, R.id.quick_login_btn, R.id.iv_back, R.id.tv_register,R.id.sina_weibo
            ,R.id.qq_account})
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
                final BmobUser user = new BmobUser();
                user.setUsername(account);
                user.setPassword(password);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            isLogin = true;
                            onBackPressed();
                        } else {
                            isLogin = false;
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
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.sina_weibo:
                WeiboUtil.loginWeibo(LoginActivity.this, new com.naf.groupbuying.bean.weibo.ISinaLogin() {
                    @Override
                    public void weiboLoginSuccess() {
                        WeiboUtil.getWeiboInfo(LoginActivity.this, new ISinaInfo() {
                            @Override
                            public void getWBInfoSuccess(User user) {
                                isLogin=true;
                                EventBus.getDefault().post(new LoginPost(isLogin,user.screen_name));
                                finish();
                            }

                            @Override
                            public void getWBInfoFailure() {

                            }
                        });
                    }

                    @Override
                    public void weiboLoginFarlure() {
                        Toast.makeText(LoginActivity.this, "ee", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.qq_account:
                if (!mTencent.isSessionValid()) {
                    mTencent.login(this, "all", loginListener);
                } else {
                    if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                        mTencent.logout(this);
                        mTencent.login(this, "all", loginListener);
                        isServerSideLogin = false;
                        return;
                    }
                    mTencent.logout(this);
                    updateUserInfo();
                }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new LoginPost(isLogin,""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (WeiboUtil.mSsoHandler != null) {
            WeiboUtil.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    IUiListener loginListener = new BaseUiListener(LoginActivity.this) {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(final Object response) {
                    JSONObject object= (JSONObject)response;
                    if(object.has("nickname")){
                        isLogin=true;
                        try {
                            EventBus.getDefault().post(new LoginPost(isLogin,object.getString("nickname")));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }

}
