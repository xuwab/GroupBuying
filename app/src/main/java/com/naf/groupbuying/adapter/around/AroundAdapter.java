package com.naf.groupbuying.adapter.around;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naf.groupbuying.R;
import com.naf.groupbuying.entity.GoodsInfo;

import java.util.List;

/**
 * Created by naf on 2016/12/5.
 */

public class AroundAdapter extends RecyclerView.Adapter<AroundAdapter.AroundHolder> {

    private List<GoodsInfo.ResultBean.GoodlistBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public AroundAdapter(Context context, List<GoodsInfo.ResultBean.GoodlistBean> list){
        this.mContext=context;
        this.mList=list;
        mLayoutInflater=LayoutInflater.from(mContext);
    }
    @Override
    public AroundAdapter.AroundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.goods_list_item,parent,false);
        return new AroundHolder(view);
    }

    @Override
    public void onBindViewHolder(AroundAdapter.AroundHolder holder, int position) {
        GoodsInfo.ResultBean.GoodlistBean goodlistBean=mList.get(position);
        holder.tvTitle.setText(goodlistBean.getTitle());
        holder.draweeView.setImageURI(goodlistBean.getImages().get(0).getImage());
        holder.tvPrice.setText(goodlistBean.getPrice());
        holder.tvContent.setText(goodlistBean.getShort_title());
        holder.tvValue.setText(goodlistBean.getValue());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AroundHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private SimpleDraweeView draweeView;
        private TextView tvPrice;
        private TextView tvContent;
        private TextView tvValue;
        public AroundHolder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.title);
            draweeView= (SimpleDraweeView) itemView.findViewById(R.id.iv_icon2);
            tvPrice= (TextView) itemView.findViewById(R.id.price);
            tvContent= (TextView) itemView.findViewById(R.id.tv_content);
            tvValue= (TextView) itemView.findViewById(R.id.value);
        }
    }
}
