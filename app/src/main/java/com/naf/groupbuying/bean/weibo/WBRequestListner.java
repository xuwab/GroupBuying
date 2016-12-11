package com.naf.groupbuying.bean.weibo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by Administrator on 0003.
 */
public class WBRequestListner implements RequestListener {
    private Context mContext;
    private ISinaInfo mISinaInfo;

    public WBRequestListner(Context context, ISinaInfo ISinaInfo) {
        mContext = context;
        mISinaInfo = ISinaInfo;
    }

    @Override
    public void onComplete(String response) {
        if (!TextUtils.isEmpty(response)) {
            // 调用 User#parse 将JSON串解析成User对象
            User user = User.parse(response);
            if (user != null) {
                mISinaInfo.getWBInfoSuccess(user);
            } else {
                mISinaInfo.getWBInfoFailure();
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        ErrorInfo info = ErrorInfo.parse(e.getMessage());
        Log.e("onWeiboException ", "onWeiboException " + info);
    }
}
