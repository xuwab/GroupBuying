package com.naf.groupbuying.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.naf.groupbuying.BmodTest.BmodTest;
import com.naf.groupbuying.BmodTest.BmodTest;
import com.naf.groupbuying.R;
import com.naf.groupbuying.bean.ContantsPool;
import com.naf.groupbuying.fragment.AroundFragment;
import com.naf.groupbuying.fragment.MainFragment;
import com.naf.groupbuying.fragment.MineFragment;
import com.naf.groupbuying.fragment.MoreFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements ContantsPool{

    @BindView(R.id.tabHost2)
    FragmentTabHost mTabHost;

    private Class[] fragments = new Class[]{
            MainFragment.class,
            AroundFragment.class,
            MineFragment.class,
            MoreFragment.class};
    private int[] images = new int[]{
            R.drawable.ic_home_tab_index_selector,
            R.drawable.ic_home_tab_near_selector,
            R.drawable.ic_home_tab_my_selector,
            R.drawable.ic_home_tab_more_selector
    };

    private long currentTime = Calendar.getInstance().getTimeInMillis() - 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragmentTabHost();
//        initState();
//        startActivity(new Intent(this, BmodTest.class));
    }


    private void initFragmentTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < title.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.tab_item, null);
            ImageView tab_iv = (ImageView) view.findViewById(R.id.iv);
            TextView tab_tv = (TextView) view.findViewById(R.id.tv);
            tab_iv.setImageResource(images[i]);
            tab_tv.setText(title[i]);
            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(view),fragments[i], null);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = Calendar.getInstance().getTimeInMillis();
                if ((secondTime - currentTime) < 2000) {
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Clicking again to exit", Toast.LENGTH_SHORT).show();
                }
                currentTime = secondTime;
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initState() {
//       //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            //
//            LinearLayout linear_bar = (LinearLayout)findViewById(R.id.ll_bar);
//            linear_bar.setVisibility(View.VISIBLE);
//            //获取到状态栏的高度
//            int statusHeight = getStatusBarHeight();
//            //动态的设置隐藏布局的高度
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
//            params.height = statusHeight;
//            linear_bar.setLayoutParams(params);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
