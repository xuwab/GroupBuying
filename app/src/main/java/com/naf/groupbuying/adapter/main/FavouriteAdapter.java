package com.naf.groupbuying.adapter.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naf.groupbuying.R;
import com.naf.groupbuying.adapter.LvCommonAdapter;
import com.naf.groupbuying.entity.GoodsInfo;
import com.naf.groupbuying.holder.LvHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class FavouriteAdapter extends LvCommonAdapter{
    private List<GoodsInfo.ResultBean.GoodlistBean> mList;
    private Context context;
    public FavouriteAdapter(Context context,List datas) {
        super(datas);
        this.mList=datas;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LvHolder holder=LvHolder.getHolder(context,convertView, R.layout.goods_list_item,null);
        GoodsInfo.ResultBean.GoodlistBean goodlistBean=mList.get(position);
        TextView tvTitle=holder.getView(R.id.title);
        tvTitle.setText(goodlistBean.getTitle());

        SimpleDraweeView draweeView=holder.getView(R.id.iv_icon2);
        draweeView.setImageURI(goodlistBean.getImages().get(0).getImage());

        TextView tvPrice=holder.getView(R.id.price);
        tvPrice.setText(goodlistBean.getPrice());

        TextView tvContent=holder.getView(R.id.tv_content);
        tvContent.setText(goodlistBean.getShort_title());

        TextView tvValue=holder.getView(R.id.value);
        tvValue.setText(goodlistBean.getValue());


        return holder.getConvertView();
    }
}
