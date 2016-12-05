package com.naf.groupbuying.activity.Collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naf.groupbuying.R;
import com.naf.groupbuying.adapter.Collection.CollectAdapter;
import com.naf.groupbuying.entity.FavorInfo;
import com.naf.groupbuying.listner.Bmob.BmobDeleteListener;
import com.naf.groupbuying.listner.Bmob.BmobQueryListener;
import com.naf.groupbuying.utils.BmobUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by naf on 2016/12/5.
 */

public class CollectionActivity extends AppCompatActivity {
    private static final String TAG = "collect";
    @BindView(R.id.list_collect)
    ListView listCollect;
    @BindView(R.id.edit_collect)
    TextView editCollect;
    @BindView(R.id.all_select)
    TextView allSelect;
    @BindView(R.id.rl_edit)
    RelativeLayout rlEdit;

    private List<FavorInfo> mFavorInfos = new ArrayList<>();
    private CollectAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        initView();
    }

    private void initView() {
        mAdapter = new CollectAdapter(this, mFavorInfos);
        listCollect.setAdapter(mAdapter);
    }

    private void initData() {
        BmobUtils.getInstance(new BmobQueryListener() {
            @Override
            public void querySuccess() {
                Toast.makeText(CollectionActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void queryError() {
                Toast.makeText(CollectionActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }).queryAndGetFavors();
        Toast.makeText(this, "" + mFavorInfos.size(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getList(List list) {
        mFavorInfos.clear();
        mFavorInfos.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BmobUtils.queryIndex = 0;
    }

    @OnClick({R.id.iv_back, R.id.edit_collect, R.id.all_select, R.id.delete_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.edit_collect:
                if(editCollect.getText().equals("编辑")){
                    rlEdit.setVisibility(View.VISIBLE);
                    for(int i=0;i<mFavorInfos.size();i++){
                        View viewChild=listCollect.getChildAt(i);
                        CheckBox checkBox= (CheckBox) viewChild.findViewById(R.id.cb_good);
                        checkBox.setVisibility(View.VISIBLE);
                    }
                    editCollect.setText("完成");
                }else {
                    rlEdit.setVisibility(View.GONE);
                    for(int i=0;i<mFavorInfos.size();i++){
                        View viewChild=listCollect.getChildAt(i);
                        CheckBox checkBox= (CheckBox) viewChild.findViewById(R.id.cb_good);
                        checkBox.setVisibility(View.GONE);
                    }
                    editCollect.setText("编辑");
                }
                break;
            case R.id.all_select:
                if(allSelect.getText().equals("全选")){
                    for(int i=0;i<mFavorInfos.size();i++){
                        View viewChild=listCollect.getChildAt(i);
                        CheckBox checkBox= (CheckBox) viewChild.findViewById(R.id.cb_good);
                        checkBox.setChecked(true);
                    }
                    allSelect.setText("取消全选");
                }else {
                    for(int i=0;i<mFavorInfos.size();i++){
                        View viewChild=listCollect.getChildAt(i);
                        CheckBox checkBox= (CheckBox) viewChild.findViewById(R.id.cb_good);
                        checkBox.setChecked(false);
                    }
                    allSelect.setText("全选");
                }

                break;
            case R.id.delete_collect:
                List<String> listDel=new ArrayList<>();
                List<FavorInfo> listFavor=new ArrayList<>();
                for(int i=0;i<mFavorInfos.size();i++){
                    View viewChild=listCollect.getChildAt(i);
                    CheckBox checkBox= (CheckBox) viewChild.findViewById(R.id.cb_good);
                    if(checkBox.isChecked()){
                        listDel.add(mFavorInfos.get(i).getGoodId());
                        listFavor.add(mFavorInfos.get(i));
                    }
                }
                mFavorInfos.removeAll(listFavor);
                mAdapter.notifyDataSetChanged();
                BmobUtils.getInstance(new BmobDeleteListener() {
                    @Override
                    public void deleteSuccess() {
                        Toast.makeText(CollectionActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void deleteError() {

                    }
                }).deleteFavor("goodId",listDel);
                break;
        }
    }
}
