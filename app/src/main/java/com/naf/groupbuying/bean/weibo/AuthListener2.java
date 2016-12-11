package com.naf.groupbuying.bean.weibo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.naf.groupbuying.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by Administrator on 0003.
 */
public class AuthListener2 implements WeiboAuthListener {


    private Oauth2AccessToken mAccessToken;
    private Context mContext;
    private ISinaLogin mISinaLogin;

    public AuthListener2(Context context,Oauth2AccessToken accessToken, ISinaLogin ISinaLogin ) {
        mContext = context;
        mAccessToken = accessToken;
        mISinaLogin = ISinaLogin;
    }

    @Override
    public void onComplete(Bundle values) {
        // 从 Bundle 中解析 Token
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {
            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
            mISinaLogin.weiboLoginSuccess();

        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = values.getString("code");
            String message = mContext.getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            mISinaLogin.weiboLoginFarlure();
        }
    }
    @Override
    public void onWeiboException(WeiboException e) {
        Log.e("onWeiboException ", "onWeiboException " + e.getMessage());
    }
    @Override
    public void onCancel() {
        Toast.makeText(mContext,
                R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
    }
}
