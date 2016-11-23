package com.naf.groupbuying.listner.around;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.naf.groupbuying.R;
import com.naf.groupbuying.activity.guide.StartActivity;
import com.naf.groupbuying.activity.map.LocationActivity;
import com.naf.groupbuying.fragment.AroundFragment;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ToolMenuClickListener implements Toolbar.OnMenuItemClickListener {
    private Context context;
    public ToolMenuClickListener(Context context){
        this.context=context;
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_map:
                context.startActivity(new Intent(context, LocationActivity.class));
                break;
        }
        return true;
    }
}
