package com.naf.groupbuying.listner.Bmob;

/**
 * Created by Administrator on 2016/12/1.
 */

public interface BmobListener {
    void sendMessageSuccess();
    void loginSuccess();
    void loginError();
    void registerSuccess();
    void registerError();
}
