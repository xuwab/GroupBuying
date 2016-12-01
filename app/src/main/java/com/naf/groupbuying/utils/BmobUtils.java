package com.naf.groupbuying.utils;


import com.naf.groupbuying.entity.User;
import com.naf.groupbuying.listner.Bmob.BmobListener;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/12/1.
 */

public class BmobUtils {
    private static BmobUtils mBmobUtils;
    private static BmobListener mBmobListener;

    public static synchronized BmobUtils getInstance(BmobListener bmobListener){
        mBmobListener=bmobListener;
        if(mBmobUtils==null){
            mBmobUtils=new BmobUtils();
        }
        return mBmobUtils;
    }


    public void registerByPhone(final String phone, final String password,final String code){
        User user = new User();
        user.setMobilePhoneNumber(phone);//设置手机号码（必填）
        user.setPassword(password);       //设置用户密码
        user.signOrLogin(code, new SaveListener<User>() {

            @Override
            public void done(User user,BmobException e) {
                if(e==null){
                    mBmobListener.registerSuccess();
                }else{
                   mBmobListener.registerError();
                }
            }
        });
    }


    public void requestCode(String phone){
        BmobSMS.requestSMSCode(phone,"Groupbuy", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    mBmobListener.sendMessageSuccess();
                }
            }
        });
    }


    public void loginByPhoneCode(final String phone, final String code){
        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    mBmobListener.loginSuccess();
                }else {
                    mBmobListener.loginError();
                }
            }
        });
    }

    public void loginByUserName(final String username, final String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    mBmobListener.loginSuccess();
                }else{
                    mBmobListener.loginError();
                }
            }
        });
    }
}
