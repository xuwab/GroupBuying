package com.naf.groupbuying.okhttp;

import android.content.Context;


import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by naf on 2016/12/14.
 */

public class OkhttpServer {
    private  OkHttpClient mClient;

    private static OkhttpServer mOkhttpServer;

    private OkhttpServer(){
        mClient=new OkHttpClient();
    }

    public synchronized static OkhttpServer getInstance(){
        if(mOkhttpServer ==null)
            mOkhttpServer =new OkhttpServer();
        return mOkhttpServer;
    }

    /***
     * 添加一个请求到队列中的
     */
    public Call add(Context context, okhttp3.Request request
            , HttpListner httpListner, boolean canCancle, boolean isLoading){
        Call call=mClient.newCall(request);
        call.enqueue(new OkHttpCallBack(context,httpListner,canCancle,isLoading));
        return call;
    }
}
