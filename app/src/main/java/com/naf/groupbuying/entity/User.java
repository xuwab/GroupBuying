package com.naf.groupbuying.entity;

/**
 * Created by Administrator on 2016/11/30.
 */

public class User {
    private String userName;
    private boolean isLoginSuccess;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }
}
