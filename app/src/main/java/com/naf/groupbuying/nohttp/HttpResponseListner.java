package com.naf.groupbuying.nohttp;

import android.content.Context;
import android.content.DialogInterface;

import com.naf.groupbuying.widget.WaitDialog;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by hongkl on 16/8/2.
 */
public class HttpResponseListner<T> implements OnResponseListener<T> {

    private HttpListner<T> mListner;

    private WaitDialog mWaitDialog;

    private boolean isLoading;

    private Request<T> mRequest;

    public HttpResponseListner(Context context, Request<T> request, HttpListner<T> listner, boolean canCancle, boolean isLoading) {
        this.mRequest = request;
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
        mListner = listner;
    }

    @Override
    public void onStart(int what) {

        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing()) {

            mWaitDialog.show();
        }
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mListner != null) {

            mListner.onSucceed(what, response);
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        if (mListner != null) {

            mListner.onFailed(what, url, tag, exception, responseCode, networkMillis);
        }
    }

    @Override
    public void onFinish(int what) {
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing()) {

            mWaitDialog.cancel();
        }
    }
}
