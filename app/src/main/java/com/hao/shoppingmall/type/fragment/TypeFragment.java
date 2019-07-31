package com.hao.shoppingmall.type.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.base.BaseFragment;

import java.util.Objects;

public class TypeFragment extends BaseFragment {

    private static final String TAG = TypeFragment.class.getSimpleName();     private TextView textView;

    private FrameLayout fl_type;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        fl_type = view.findViewById(R.id.fl_type);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fl_type, new ListFragment()).commit();
    }
}
