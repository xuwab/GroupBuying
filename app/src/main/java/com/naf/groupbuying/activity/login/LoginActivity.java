package com.naf.groupbuying.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.naf.groupbuying.R;
import com.naf.groupbuying.entity.LoginInfo;
import com.naf.groupbuying.nohttp.CallServer;
import com.naf.groupbuying.nohttp.HttpListner;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naf on 2016/11/27.
 */

public class LoginActivity extends AppCompatActivity implements HttpListner<String> {
    @BindView(R.id.et_quick_phone)
    EditText etQuickPhone;
    @BindView(R.id.et_quick_code)
    EditText etQuickCode;
    @BindView(R.id.quick_login_btn)
    Button quickLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.quick_login_btn)
    public void onClick() {
        String account=etQuickPhone.getText().toString();
        String password=etQuickCode.getText().toString();

        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            Toast.makeText(this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String url="https://api.bmob.cn/1/login?"+"username"+"="+account+"&"+"password"+"="+password;
        Request<String> request=NoHttp.createStringRequest(url, RequestMethod.GET);

//        String body="{\"username\":\""+account+"\",\"password\":\""+password+"\"}";
//        request.setDefineRequestBodyForJson(body);
        request.addHeader("X-Bmob-Application-Id","880eef1e95585d7b55891ade112efdbb");
        request.addHeader("X-Bmob-REST-API-Key","3d40146c6d4d91ce91319aee5db2a266");
        request.addHeader("Content-Type","application/json");
        CallServer.getInstance().add(LoginActivity.this,0,request,this,true,true);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what){
            case 0:
                Gson gson=new Gson();
                LoginInfo loginInfo=gson.fromJson(response.get(),LoginInfo.class);
                String userName=loginInfo.getUsername();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "登录成功  ",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        switch (what){
            case 0:
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
