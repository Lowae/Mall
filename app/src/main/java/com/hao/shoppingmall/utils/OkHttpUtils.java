package com.hao.shoppingmall.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {



    private static final int TIME_OUT = 30;
    private static OkHttpClient okHttpClient = null;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClient = okHttpClientBuilder.build();
    }


    public static void sendRequest(Request request, JsonCallback jsonCallback){
        okHttpClient.newCall(request).enqueue(jsonCallback);
    }

    public static Request createGetRequest(String url){
        return new Request.Builder().url(url).get().build();
    }

    public static class JsonCallback implements Callback{

        private Handler mDeliveryHandler;
        private DisposeDataListener mListener;
        private Class<?> mClass;

        public JsonCallback(DisposeDataHandle handle){
            mDeliveryHandler = new Handler(Looper.getMainLooper());
            mListener = handle.mListener;
            mClass = handle.mClass;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull final IOException e) {
            mDeliveryHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onFailure(e);
                }
            });
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            assert response.body() != null;
            final String result = response.body().string();
            mDeliveryHandler.post(new Runnable() {
                @Override
                public void run() {
                    handleResponse(result);
                }
            });
        }

        private void handleResponse(Object responseObj){
            if (responseObj == null || responseObj.toString().isEmpty()){
                mListener.onFailure(new Exception(""));
                return;
            }
            try {
                String result = responseObj.toString();
                if (mClass == null){
                    mListener.onSuccess(result);
                }else {
                    Object obj = JSON.parseObject(result, mClass);
                    if (obj != null){
                        mListener.onSuccess(obj);
                    }else {
                        mListener.onFailure(new Exception());
                    }
                }
            }catch (Exception e){
                mListener.onFailure(e);
                e.printStackTrace();
            }
        }
    }

    public static class DisposeDataHandle<T>{
        public DisposeDataListener<T> mListener = null;
        public Class<?> mClass = null;
        public String mSource = null;

        public DisposeDataHandle(DisposeDataListener<T> listener){
            mListener = listener;
        }

        public DisposeDataHandle(DisposeDataListener<T> listener, Class<?> clazz){
            mListener = listener;
            mClass = clazz;
        }

        public DisposeDataHandle(DisposeDataListener<T> listener, String source){
            mListener = listener;
            mSource = source;
        }
    }

    public interface DisposeDataListener<T>{
        void onSuccess(T t);

        void onFailure(Exception e);
    }
}
