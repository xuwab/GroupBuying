package com.naf.groupbuying.utils;


import android.util.Log;

import com.naf.groupbuying.entity.DetailInfo;
import com.naf.groupbuying.entity.FavorInfo;
import com.naf.groupbuying.entity.User;
import com.naf.groupbuying.listner.Bmob.BmobListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.R.id.list;
import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/12/1.
 */

public class BmobUtils {
    public static BmobUtils mBmobUtils;
    public static BmobListener mBmobListener;
    public static int queryIndex;
    public static int userIndex;

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
                }else mBmobListener.loginError();
            }
        });
    }

    public void insertFavor(final DetailInfo favorInfo){
        BmobQuery<FavorInfo> favorInfoBmobQuery=new BmobQuery<>();
        favorInfoBmobQuery.addWhereEqualTo("goodId",favorInfo.getResult().getGoods_id());
        favorInfoBmobQuery.findObjects(new FindListener<FavorInfo>() {
            @Override
            public void done(List<FavorInfo> list, BmobException e) {
                if(e==null){
                    if(list.size()==0){
                        User user = BmobUser.getCurrentUser(User.class);
                        FavorInfo favorInfo1 = new FavorInfo();
//将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                        BmobRelation relation = new BmobRelation();
//将当前用户添加到多对多关联中
                        relation.add(user);
//多对多关联指向`post`的`likes`字段
                        favorInfo1.setFavor(relation);
                        favorInfo1.setPrice(favorInfo.getResult().getPrice());
                        favorInfo1.setIvImage(favorInfo.getResult().getImages().get(0).getImage());
                        favorInfo1.setTitle(favorInfo.getResult().getTitle());
                        favorInfo1.setShortTitle(favorInfo.getResult().getShort_title());
                        favorInfo1.setValue(favorInfo.getResult().getValue());
                        favorInfo1.setGoodId(favorInfo.getResult().getGoods_id());
                        favorInfo1.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    mBmobListener.addFavorSuccess();
                                }else mBmobListener.addFavorError();
                            }
                        });
                    }else {
                        FavorInfo favorInfo1 = new FavorInfo();
                        favorInfo1.setObjectId(list.get(0).getObjectId());
//将用户B添加到Post表中的likes字段值中，表明用户B喜欢该帖子
                        BmobRelation relation = new BmobRelation();
//将用户B添加到多对多关联中
                        User user=BmobUser.getCurrentUser(User.class);
                        relation.add(user);
//多对多关联指向`post`的`likes`字段
                        favorInfo1.setFavor(relation);
                        favorInfo1.update(new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    mBmobListener.addFavorSuccess();
                                }else{
                                    mBmobListener.addFavorError();
                                }
                            }

                        });
                    }
                }else {
                    mBmobListener.addFavorError();
                }
            }
        });
    }

    public void queryFavor(String key,String value){
        // 查询喜欢这个帖子的所有用户，因此查询的是用户表

        BmobQuery<FavorInfo> favorInfoBmobQuery=new BmobQuery<>();
        favorInfoBmobQuery.addWhereEqualTo(key,value);
        favorInfoBmobQuery.findObjects(new FindListener<FavorInfo>() {
            @Override
            public void done(List<FavorInfo> list, BmobException e) {
                if(e==null){
                    BmobQuery<User> query = new BmobQuery<User>();
                    FavorInfo favorInfo = new FavorInfo();
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
                    favorInfo.setObjectId(list.get(0).getObjectId());
                    query.addWhereRelatedTo("favor", new BmobPointer(favorInfo));
                    query.findObjects(new FindListener<User>() {

                        @Override
                        public void done(List<User> object,BmobException e) {
                            if(e==null){
                                Log.e(TAG, "done: "+object.size() );
                                if(object.get(0).getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())){
                                    mBmobListener.querySuccess();
                                }
                            }else {
                                mBmobListener.queryError();
                            }

                        }

                    });
                }
            }
        });
    }

    public void queryAndGetFavors(){
        // 查询用户喜欢的所有商品
        final List<FavorInfo> favorInfoList=new ArrayList<>();
        BmobQuery<FavorInfo> favorInfoBmobQuery=new BmobQuery<>();
        favorInfoBmobQuery.findObjects(new FindListener<FavorInfo>() {
            @Override
            public void done(final List<FavorInfo> list, BmobException e) {
                if(e==null){
                    Log.e(TAG, "done: ========"+list.size() );
                    for(int i=0;i<list.size();i++){
                        final BmobQuery<User> query = new BmobQuery<User>();
                        FavorInfo favorInfo = new FavorInfo();
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
                        favorInfo=list.get(i);
                        Log.e(TAG, "done: !!!!!!!!!!!!"+list.get(i) .getObjectId()+"______"+i);
                        query.addWhereRelatedTo("favor", new BmobPointer(favorInfo));
                        final FavorInfo finalFavorInfo = favorInfo;
                        query.findObjects(new FindListener<User>() {

                            @Override
                            public  void done(List<User> object,BmobException e) {
                                if(e==null){
                                    queryIndex++;
                                    Log.e(TAG, "done: ~~~~~~~~~~~~~~~queryIndex"+queryIndex+"~~~~~~~~~~~`"+object.size() );
                                    for(int j=0;j<object.size();j++){
                                        if(object.get(j).getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())){
                                            mBmobListener.querySuccess();
                                            favorInfoList.add(finalFavorInfo) ;
                                            EventBus.getDefault().post(favorInfoList);
                                            userIndex=0;
                                            break;
                                        }
                                    }
                                }else {
                                    mBmobListener.queryError();
                                }
                            }
                        });
                    }
                }else {
                    mBmobListener.queryError();
                }
            }
        });
    }


    public void deleteFavor(String key,List<String> values){
        for(int i=0;i<values.size();i++ ){
            BmobQuery<FavorInfo> favorInfoBmobQuery=new BmobQuery<>();
            favorInfoBmobQuery.addWhereEqualTo(key,values.get(i));
            favorInfoBmobQuery.findObjects(new FindListener<FavorInfo>() {
                @Override
                public void done(List<FavorInfo> list, BmobException e) {
                    if(e==null){
                        FavorInfo post = new FavorInfo();
                        post.setObjectId(list.get(0).getObjectId());
                        User user = BmobUser.getCurrentUser(User.class);
                        BmobRelation relation = new BmobRelation();
                        relation.remove(user);
                        post.setFavor(relation);
                        post.update(new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    mBmobListener.deleteSuccess();
                                }else{
                                    mBmobListener.deleteError();
                                }
                            }

                        });
                    }else mBmobListener.deleteError();
                }
            });
        }


    }
}
