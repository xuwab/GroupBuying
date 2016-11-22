package com.naf.groupbuying.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naf.groupbuying.R;
import com.naf.groupbuying.entity.HomeIconInfo;

import java.util.ArrayList;
import java.util.List;

public class GuideGridAdapter extends BaseAdapter {

    private List<HomeIconInfo> itemData = new ArrayList<>();
    private Context mContext;

    public GuideGridAdapter(List<HomeIconInfo> itemData, Context context) {
        this.itemData = itemData;
        mContext = context;
    }

    @Override
    public int getCount() {
        return itemData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);

        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv);
        imageView.setImageResource(itemData.get(i).getIconID());
        TextView tv = (TextView) inflate.findViewById(R.id.tv);
        tv.setText(itemData.get(i).getIconName());
        return inflate;
    }
}