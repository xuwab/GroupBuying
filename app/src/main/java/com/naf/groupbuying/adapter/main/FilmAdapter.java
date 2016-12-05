package com.naf.groupbuying.adapter.main;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naf.groupbuying.R;
import com.naf.groupbuying.entity.FilmInfo;

import java.util.List;

/**
 * Created by naf on 2016/12/5.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {
    private List<FilmInfo.ResultBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public FilmAdapter(Context context, List<FilmInfo.ResultBean> list){
        this.mContext=context;
        this.mList=list;
        mLayoutInflater=LayoutInflater.from(mContext);
    }
    @Override
    public FilmAdapter.FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.film_item,parent,false);
        FilmHolder holder=new FilmHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(FilmAdapter.FilmHolder holder, int position) {
        FilmInfo.ResultBean resultBean=mList.get(position);
        Uri uri = Uri.parse(resultBean.getImageUrl());
        holder.ivFilmIcon.setImageURI(uri);
        holder.tvFilmName.setText(resultBean.getFilmName());
        holder.tvFilmCount.setText(resultBean.getGrade() + "分");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class FilmHolder extends RecyclerView.ViewHolder {
        private  SimpleDraweeView ivFilmIcon;
        private TextView tvFilmName;
        private TextView tvFilmCount;
        public FilmHolder(View itemView) {
            super(itemView);
            ivFilmIcon = (SimpleDraweeView) itemView.findViewById(R.id.iv_filmIcon);
            tvFilmName = (TextView) itemView.findViewById(R.id.tv_filmName);
            tvFilmCount = (TextView) itemView.findViewById(R.id.tv_film_count);
        }
    }
}
