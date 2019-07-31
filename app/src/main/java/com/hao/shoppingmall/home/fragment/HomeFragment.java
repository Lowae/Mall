package com.hao.shoppingmall.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.base.BaseFragment;
import com.hao.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.hao.shoppingmall.home.bean.ResultBeanData;
import com.hao.shoppingmall.utils.Constants;
import com.hao.shoppingmall.utils.OkHttpUtils;

public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;

    private HomeFragmentAdapter adapter;

    private ResultBeanData.ResultBean resultBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = view.findViewById(R.id.rv_home);
        ib_top = view.findViewById(R.id.ib_top);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);

        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
        Log.e(TAG, "主页数据被初始化了");
    }

    private void initListener(){
        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.scrollToPosition(0);
            }
        });

        //搜索的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromNet(){
        String url = Constants.HOME_URL;
        OkHttpUtils.sendRequest(OkHttpUtils.createGetRequest(url), new OkHttpUtils.JsonCallback(new OkHttpUtils.DisposeDataHandle<>(new OkHttpUtils.DisposeDataListener<ResultBeanData>() {
            @Override
            public void onSuccess(ResultBeanData resultBeanData) {
                resultBean = resultBeanData.getResult();
                adapter = new HomeFragmentAdapter(mContext, resultBean);
                rvHome.setAdapter(adapter);
                rvHome.setLayoutManager(new GridLayoutManager(mContext, 1));
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "Exception", e);
            }
        }, ResultBeanData.class)));

    }
}
