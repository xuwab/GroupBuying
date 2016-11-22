package com.naf.groupbuying.activity.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.naf.groupbuying.R;
import com.naf.groupbuying.activity.MainActivity;

/**
 * Created by naf on 2016/11/10.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        final SharedPreferences spMain=getPreferences(MODE_PRIVATE);
//        boolean isFinishMain=spMain.getBoolean("isFinishMainByBack",false);
//        Toast.makeText(StartActivity.this, String.valueOf(isFinishMain), Toast.LENGTH_SHORT).show();
//        if(!isFinishMain){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sp = getPreferences(MODE_PRIVATE);
                    boolean isFirst=sp.getBoolean("isFirst",true);
                    Intent intent=new Intent();
                    if(isFirst){
                        sp.edit().putBoolean("isFirst",false).commit();
//                        spMain.edit().putBoolean("isFinishMainByBack",false).commit();
                        intent.setClass(StartActivity.this,GuideActivity.class);
                    }else intent.setClass(StartActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
//        }else {
//            startActivity(new Intent(StartActivity.this,MainActivity.class));
//            finish();
//        }

    }
}
