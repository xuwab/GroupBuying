package com.naf.groupbuying;

import android.app.Application;

//import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.tauth.Tencent;
import com.yolanda.nohttp.NoHttp;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

import static android.provider.UserDictionary.Words.APP_ID;

//import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/11/22.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        Fresco.initialize(this);

        //百度地图初始化
        SDKInitializer.initialize(this);
        Bmob.initialize(this,"880eef1e95585d7b55891ade112efdbb");


    }
}
