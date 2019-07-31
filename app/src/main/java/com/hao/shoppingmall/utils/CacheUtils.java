package com.hao.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {

    public static final String CART = "cart";

    public static String getString(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void saveString(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }
}
