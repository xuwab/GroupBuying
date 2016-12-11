package com.naf.groupbuying.bean.Qq;

/**
 * Created by naf on 2016/12/11.
 */

import android.app.Activity;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class  BaseUiListener implements IUiListener {

    private Activity mActivity;
    public BaseUiListener(Activity mActivity){
        this.mActivity=mActivity;
    }
    @Override
    public void onComplete(Object response) {
        if (null == response) {
            Util.showResultDialog(mActivity, "返回为空", "登录失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            Util.showResultDialog(mActivity, "返回为空", "登录失败");
            return;
        }
//        Util.showResultDialog(mActivity, response.toString(), "登录成功");
        // 有奖分享处理
        doComplete((JSONObject)response);
    }

    protected void doComplete(JSONObject values) {

    }

    @Override
    public void onError(UiError e) {
        Util.toastMessage(mActivity, "onError: " + e.errorDetail);
        Util.dismissDialog();
    }

    @Override
    public void onCancel() {
        Util.toastMessage(mActivity, "onCancel: ");
        Util.dismissDialog();
    }
}