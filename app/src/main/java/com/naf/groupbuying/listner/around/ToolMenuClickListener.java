package com.naf.groupbuying.listner.around;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.naf.groupbuying.R;

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
                Toast.makeText(context, "map", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
