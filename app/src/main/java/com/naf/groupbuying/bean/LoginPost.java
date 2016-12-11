package com.naf.groupbuying.bean;

/**
 * Created by naf on 2016/12/11.
 */

public class LoginPost {
    private boolean isLogin;
    private String name;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginPost(boolean isLogin, String name) {

        this.isLogin = isLogin;
        this.name = name;
    }
}
