package com.hao.shoppingmall.home.fragment;

import android.os.SystemClock;
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
import com.hitomi.refresh.view.FunGameRefreshView;

public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private FunGameRefreshView funGameRefreshView;

    private HomeFragmentAdapter adapter;

    private ResultBeanData.ResultBean resultBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = view.findViewById(R.id.rv_home);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        funGameRefreshView = view.findViewById(R.id.refresh_fun_game);
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        funGameRefreshView.setOnRefreshListener(new FunGameRefreshView.FunGameRefreshListener() {
            @Override
            public void onPullRefreshing() {
                SystemClock.sleep(2000);
            }

            @Override
            public void onRefreshComplete() {
                adapter.notifyDataSetChanged();
            }
        });
        getDataFromNet();
        Log.e(TAG, "主页数据被初始化了");
    }

    private void initListener(){

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
