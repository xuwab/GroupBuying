package com.naf.groupbuying.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naf.groupbuying.R;
import com.naf.groupbuying.fragment.MainFragment;
import com.naf.groupbuying.test.ToolbarActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity {

    @BindView(R.id.tabHost2)
    FragmentTabHost mTabHost;
    private int[] images = new int[]{
            R.drawable.ic_home_tab_index_selector,
            R.drawable.ic_home_tab_near_selector,
            R.drawable.ic_home_tab_my_selector,
            R.drawable.ic_home_tab_more_selector
    };
    private String[] text = new String[]{"首页", "周边", "我的", "更多"};
    //    private Class[] fragments = new Class[]{MainFragment.class, AroundFragment.class, MineFragment.class, MoreFragment.class};

    private long currentTime = Calendar.getInstance().getTimeInMillis() - 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < text.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.tab_item, null);
            ImageView tab_iv = (ImageView) view.findViewById(R.id.iv);
            TextView tab_tv = (TextView) view.findViewById(R.id.tv);
            tab_iv.setImageResource(images[i]);
            tab_tv.setText(text[i]);
            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(view),MainFragment.class, null);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                new AlertDialog.Builder(this)
//                        .setMessage("是否退出？")
//                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences sp=getPreferences(MODE_PRIVATE);
//                        sp.edit().putBoolean("isFinishMainByBack",true).commit();
//                        Toast.makeText(MainActivity.this, String.valueOf(sp.getBoolean("isFinishMainByBack",true)), Toast.LENGTH_SHORT).show();
//                        dialog.cancel();
//                        finish();
//                    }
//                }).show();


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
}
