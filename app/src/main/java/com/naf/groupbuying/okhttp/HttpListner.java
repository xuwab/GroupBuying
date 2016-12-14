package com.naf.groupbuying.okhttp;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by naf on 16/12/2.
 */
public interface HttpListner {
    void onResponse(Call call, okhttp3.Response response);
    void onFailure(Call call, IOException e);

}
