package com.hao.shoppingmall.user.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.base.BaseFragment;
import com.hao.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.hao.shoppingmall.home.adapter.HotRecyclerViewAdapter;
import com.hao.shoppingmall.home.bean.ResultBeanData;
import com.hao.shoppingmall.home.decoration.HotRecyclerViewDecoration;
import com.hao.shoppingmall.utils.Constants;
import com.hao.shoppingmall.utils.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserFragment extends BaseFragment {

    private static final String TAG = UserFragment.class.getSimpleName();
    @BindView(R.id.ib_user_avator)
    ImageButton ibUserAvator;
    @BindView(R.id.rl_person_header)
    RelativeLayout rlPersonHeader;
    @BindView(R.id.sv_person)
    LinearLayout svPerson;
    @BindView(R.id.tv_usercenter)
    TextView tvUsercenter;
    @BindView(R.id.user_hot)
    RecyclerView rvUserHot;

    Unbinder unbinder;

    private HotRecyclerViewAdapter adapter;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String url = Constants.HOME_URL;
        OkHttpUtils.sendRequest(OkHttpUtils.createGetRequest(url), new OkHttpUtils.JsonCallback(new OkHttpUtils.DisposeDataHandle<>(new OkHttpUtils.DisposeDataListener<ResultBeanData>() {
            @Override
            public void onSuccess(ResultBeanData resultBeanData) {
                adapter = new HotRecyclerViewAdapter(mContext, resultBeanData.getResult().getHot_info());
                rvUserHot.setLayoutManager(new GridLayoutManager(mContext, 2));
                rvUserHot.setAdapter(adapter);
                rvUserHot.addItemDecoration(new HotRecyclerViewDecoration());

            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "Exception", e);
            }
        }, ResultBeanData.class)));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
