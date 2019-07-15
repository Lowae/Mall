package com.hao.shoppingmall.home.fragment;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.base.BaseFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;

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
        String url = "http://192.168.3.15:8080/atguigu/json/HOME_URL.json";
        new DataAsycnTask(mContext).execute(url);
    }

    private static class DataAsycnTask extends AsyncTask<String, Void, String>{

        private WeakReference<Context> Reference;

        DataAsycnTask(Context context){
            Reference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .get()
                        .url(strings[0])
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.e("TAG",data);
                return data;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
