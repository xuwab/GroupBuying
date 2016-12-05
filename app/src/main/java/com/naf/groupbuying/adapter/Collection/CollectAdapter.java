package com.naf.groupbuying.adapter.Collection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naf.groupbuying.R;
import com.naf.groupbuying.adapter.LvCommonAdapter;
import com.naf.groupbuying.entity.FavorInfo;
import com.naf.groupbuying.holder.LvHolder;

import java.util.List;

/**
 * Created by naf on 2016/12/5.
 */

public class CollectAdapter extends LvCommonAdapter {
    private List<FavorInfo> mFavorInfos;
    private Context mContext;
    public CollectAdapter(Context context,List datas) {
        super(datas);
        this.mFavorInfos=datas;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LvHolder holder=LvHolder.getHolder(mContext,convertView, R.layout.goods_list_item,null);

        FavorInfo favorInfo=mFavorInfos.get(position);
        TextView tvTitle=holder.getView(R.id.title);
        tvTitle.setText(favorInfo.getTitle());

        SimpleDraweeView draweeView=holder.getView(R.id.iv_icon2);
        draweeView.setImageURI(favorInfo.getIvImage());

        TextView tvPrice=holder.getView(R.id.price);
        tvPrice.setText(favorInfo.getPrice());

        TextView tvContent=holder.getView(R.id.tv_content);
        tvContent.setText(favorInfo.getShortTitle());

        TextView tvValue=holder.getView(R.id.value);
        tvValue.setText(favorInfo.getValue());
        return holder.getConvertView();
    }
}
