package com.naf.groupbuying.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.naf.groupbuying.R;
import com.naf.groupbuying.listner.around.ToolMenuClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/22.
 */

public class AroundFragment extends Fragment {

    private View mView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.supplier_list_product_tv)
    TextView mProductTv;
    @BindView(R.id.supplier_list_product)
    LinearLayout mProduct;
    @BindView(R.id.supplier_list_sort_tv)
    TextView mSortTv;
    @BindView(R.id.supplier_list_sort)
    LinearLayout mSort;
    @BindView(R.id.supplier_list_activity_tv)
    TextView mActivityTv;
    @BindView(R.id.supplier_list_activity)
    LinearLayout mActivity;
    @BindView(R.id.supplier_list_lv)
    ListView mSupplierListLv;

    private PopupWindow popupWindow;

    private ArrayList<Map<String, String>> mMenuAll;
    private ArrayList<Map<String, String>> mMenuSort;
    private ArrayList<Map<String, String>> mMenuActivity;
    private ListView mPopListview;

    private SimpleAdapter mMenuAdapter1;
    private SimpleAdapter mMenuAdapter2;
    private SimpleAdapter mMenuAdapter3;

    private int menuIndex=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化view
        mView = inflater.inflate(R.layout.fragment_around,null);
        ButterKnife.bind(this,mView);

        //toolbar设置菜单按钮
        mToolbar.inflateMenu(R.menu.around_title_menu);
        mToolbar.setOnMenuItemClickListener(new ToolMenuClickListener(getActivity()));
        mToolbar.setOnMenuItemClickListener(new ToolMenuClickListener(getActivity()));
        initData();
        initView();
        return mView;
    }



    private void initData() {
        initPopDatas();
    }

    private void initPopDatas() {
        mMenuAll=initMenu(R.array.popwin_all,"name");
        mMenuSort=initMenu(R.array.popwin_sort,"name");
        mMenuActivity=initMenu(R.array.popwin_activity,"name");
    }

    private void initView() {
        initPopWindow();
    }

    private void initPopWindow() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_around, null);
        mPopListview = (ListView) popView.findViewById(R.id.popwin_supplier_list_lv);
        popupWindow=new PopupWindow( popView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mProductTv.setTextColor(Color.parseColor("#5a5959"));
                mSortTv.setTextColor(Color.parseColor("#5a5959"));
                mActivityTv.setTextColor(Color.parseColor("#5a5959"));
            }
        });

        popView.findViewById(R.id.popwin_supplier_list_bottom)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

        mMenuAdapter1 = new SimpleAdapter(getActivity(), mMenuAll, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mMenuAdapter2 = new SimpleAdapter(getActivity(), mMenuSort, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mMenuAdapter3 = new SimpleAdapter(getActivity(), mMenuActivity, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mPopListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                switch (menuIndex){
                    case 0:
                        String currentProduct = mMenuAll.get(i).get("name");
//                        mSupplierListTitleTv.setText(currentProduct);
                        mProductTv.setText(currentProduct);
                        break;
                    case 1:
                        String currentSort = mMenuSort.get(i).get("name");
//                        mSupplierListTitleTv.setText(currentSort);
                        mSortTv.setText(currentSort);
                        break;
                    case 2:
                        String currentAct = mMenuActivity.get(i).get("name");
//                        mSupplierListTitleTv.setText(currentAct);
                        mActivityTv.setText(currentAct);
                        break;
                }
            }
        });

    }

    @OnClick({R.id.supplier_list_product, R.id.supplier_list_sort, R.id.supplier_list_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.supplier_list_product:
                mProductTv.setTextColor(Color.parseColor("#009688"));
                mPopListview.setAdapter(mMenuAdapter1);
                popupWindow.showAsDropDown(mProduct);
                menuIndex = 0;
                break;
            case R.id.supplier_list_sort:
                mSortTv.setTextColor(Color.parseColor("#009688"));
                mPopListview.setAdapter(mMenuAdapter2);
                popupWindow.showAsDropDown(mSort);
                menuIndex = 1;
                break;
            case R.id.supplier_list_activity:
                mActivityTv.setTextColor(Color.parseColor("#009688"));
                mPopListview.setAdapter(mMenuAdapter3);
                popupWindow.showAsDropDown(mActivity);
                menuIndex = 2;
                break;
        }
    }


    private ArrayList<Map<String,String>> initMenu(int array,String names){
        ArrayList<Map<String,String>> mMenu=new ArrayList<>();
        String[] menus=getResources().getStringArray(array);
        Map<String,String> map;
        for(int i=0;i<menus.length;i++){
            map=new HashMap<>();
            map.put(names,menus[i]);
            mMenu.add(map);
        }
        return mMenu;
    }
}
