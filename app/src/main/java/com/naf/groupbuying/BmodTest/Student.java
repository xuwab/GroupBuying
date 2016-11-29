package com.naf.groupbuying.BmodTest;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/29.
 */

public class Student extends BmobObject {
    private String num;
    private String hobby;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
