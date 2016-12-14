package com.naf.groupbuying.okhttp;

import android.content.Context;
import android.content.DialogInterface;

import com.naf.groupbuying.nohttp.*;
import com.naf.groupbuying.widget.WaitDialog;
import com.yolanda.nohttp.rest.Request;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by naf on 2016/12/14.
 */

public class OkHttpCallBack implements Callback {

    private WaitDialog mWaitDialog;
    private HttpListner mHttpListner;
    private boolean isLoading;

    public OkHttpCallBack(Context context,HttpListner listner, boolean canCancle, boolean isLoading) {
        this.isLoading = isLoading;
        if (context != null) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancle);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mWaitDialog.cancel();
                }
            });
        }
        mHttpListner = listner;
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(mHttpListner!=null){
            mHttpListner.onResponse(call,response);
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(mHttpListner!=null){
            mHttpListner.onFailure(call,e);
        }
    }
}
