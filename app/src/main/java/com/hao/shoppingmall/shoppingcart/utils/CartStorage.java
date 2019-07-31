package com.hao.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hao.shoppingmall.home.bean.GoodsBean;
import com.hao.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;

    private Context mContext;
    private ArrayMap<String, GoodsBean> goodsBeanArrayMap;

    private CartStorage(Context applicationContext){
        goodsBeanArrayMap = new ArrayMap<>();
        mContext = applicationContext;
        ToArrayMap();
    }

    public static CartStorage getInstance(Context mContext){
        if (instance == null){
            instance = new CartStorage(mContext.getApplicationContext());
        }
        return instance;
    }

    private void ToArrayMap(){
        List<GoodsBean> goodsBeanList = getAllData();
        for (GoodsBean goodsBean : goodsBeanList){
            goodsBeanArrayMap.put(goodsBean.getProduct_id(), goodsBean);
        }
    }

    public List<GoodsBean> getAllData(){
        String json = CacheUtils.getString(mContext, JSON_CART);
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        if (! TextUtils.isEmpty(json)){
            goodsBeanList = JSON.parseObject(json, new TypeReference<List<GoodsBean>>(){});
        }
        return goodsBeanList;
    }

    public void addData(GoodsBean goodsBean){
        GoodsBean data = goodsBeanArrayMap.get(goodsBean.getProduct_id());
        if (data != null){
            data.setNumber(data.getNumber()+goodsBean.getNumber());
            Log.d("addSubViewLayout --- ", String.valueOf(data.getNumber()));
        }else {
            data = goodsBean;
            data.setNumber(goodsBean.getNumber());
        }

        goodsBeanArrayMap.put(data.getProduct_id(), data);
        saveLocal();
    }

    public void deleteData(GoodsBean goodsBean){
        //1.内存中删除
        goodsBeanArrayMap.remove(goodsBean.getProduct_id());

        //2.把内存的保持到本地
        saveLocal();
    }

    public void updateData(GoodsBean goodsBean){
        //1.内存中更新
        goodsBeanArrayMap.put(goodsBean.getProduct_id(),goodsBean);

        //2.同步到本地
        saveLocal();
    }

    private void saveLocal() {
        //1.SparseArray转换成List
        List<GoodsBean>  goodsBeanList = new ArrayList<>(goodsBeanArrayMap.values());
        //2.使用Gson把列表转换成String类型
        String json = JSON.toJSONString(goodsBeanList);
        //3.把String数据保存
        CacheUtils.saveString(mContext,JSON_CART,json);

    }

}
