package com.naf.groupbuying.bean.weibo;

import android.app.Activity;
import android.util.Log;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.openapi.UsersAPI;

import static cn.bmob.v3.BmobRealTimeData.TAG;

/**
 * Created by naf on 2016/12/10.
 */

public class WeiboUtil {

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    public static SsoHandler mSsoHandler;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private static Oauth2AccessToken mAccessToken;

    /** 显示认证后的信息，如 AccessToken */
    private static AuthInfo mAuthInfo;

    /** 用户信息接口 */
    private static UsersAPI mUsersAPI;


    public static void initWeibo(Activity activity){
        // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(activity, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(activity, mAuthInfo);


        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(activity);

        // 获取用户信息接口
        mUsersAPI = new UsersAPI(activity, Constants.APP_KEY, mAccessToken);
    }

    public static void loginWeibo(Activity activity,ISinaLogin iSinaLogin){

        Log.e(TAG, "loginWeibo: "+"+++++++++++++++++++" );
        mSsoHandler.authorize(new AuthListener2(activity,mAccessToken,iSinaLogin));
        Log.e(TAG, "loginWeibo: "+"-------------------" );
    }

    /**
     * 获取微博用户信息
     * @param activity
     * @param iSinaInfo
     */
    public static void getWeiboInfo(Activity activity,ISinaInfo iSinaInfo){
        long uid = Long.parseLong(mAccessToken.getUid());
        mUsersAPI.show(uid, new WBRequestListner(activity,iSinaInfo));
    }
}
