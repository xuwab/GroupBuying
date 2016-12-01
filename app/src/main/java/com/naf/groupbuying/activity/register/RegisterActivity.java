package com.naf.groupbuying.activity.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.naf.groupbuying.R;
import com.naf.groupbuying.listner.Bmob.BmobListener;
import com.naf.groupbuying.listner.login.MyEditTextListener;
import com.naf.groupbuying.utils.BmobUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naf on 2016/12/1.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_check_code)
    EditText mEtCheckCode;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_repwd)
    EditText mEtRepwd;
    private BmobUtils mBmobUtils;
    private String mPhone;
    private String mCode;
    private String mPwd;
    private String mRePwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        ButterKnife.bind(this);
        initBmobUtils();
        initLisener();
        initEdit();

    }

    private void initLisener() {
        mEtPhone.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                mPhone=s.toString();
            }
        });
        mEtCheckCode.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                mCode=s.toString();
            }
        });
        mEtPwd.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                mPwd=s.toString();
            }
        });
        mEtRepwd.addTextChangedListener(new MyEditTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                mRePwd=s.toString();
            }
        });
    }

    private void initEdit() {

    }

    private void initBmobUtils() {
        mBmobUtils = BmobUtils.getInstance(new BmobListener() {
            @Override
            public void sendMessageSuccess() {
                Toast.makeText(RegisterActivity.this, "短信已经发送！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void loginSuccess() {

            }

            @Override
            public void loginError() {

            }

            @Override
            public void registerSuccess() {
                Toast.makeText(RegisterActivity.this, "验证码错误，注册失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void registerError() {
                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.bt_get_sms_code, R.id.bt_get_check_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.bt_get_sms_code:
                if(!TextUtils.isEmpty(mPhone)&& mPhone.length()==11){
                    mBmobUtils.requestCode(mPhone);
                }else {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_get_check_code:
                if(TextUtils.isEmpty(mPhone)||mPhone.length()!=11){
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(mCode)){
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(mPwd)||mPwd.length()<=6){
                    Toast.makeText(this, "请输入大于6位以上数字和字母的组合密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(mRePwd)){
                    Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(mPwd.equals(mRePwd)){
                    Toast.makeText(this, "两次密码不同，请重新确认！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBmobUtils.registerByPhone(mPhone,mPwd,mCode);
                break;
        }
    }

}
