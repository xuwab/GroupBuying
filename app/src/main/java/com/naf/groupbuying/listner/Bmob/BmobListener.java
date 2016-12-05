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
    void addFavorSuccess();
    void addFavorError();
    void querySuccess();
    void queryError();
    void deleteSuccess();
    void deleteError();
}
