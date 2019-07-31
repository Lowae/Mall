package com.hao.shoppingmall.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.base.BaseFragment;

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
    Unbinder unbinder;
    private TextView textView;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
