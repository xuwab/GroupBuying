package com.naf.groupbuying.nohttp;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by hongkl on 16/8/2.
 */
public interface HttpListner<T> {
    void onSucceed(int what, Response<T> response);
    void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);

}
