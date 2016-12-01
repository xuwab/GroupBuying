package com.naf.groupbuying.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/30.
 */

public class User extends BmobUser{
    private boolean isLoginSuccess;

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }
}
