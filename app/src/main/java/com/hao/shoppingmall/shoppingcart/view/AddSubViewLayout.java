package com.hao.shoppingmall.shoppingcart.view;

import android.content.Context;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.shoppingmall.R;

public class AddSubViewLayout extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    private OnNumberChangerListener onNumberChangerListener;

    public AddSubViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.add_sub_view, this, true);
        iv_sub = view.findViewById(R.id.iv_sub);
        tv_value = view.findViewById(R.id.tv_value);
        iv_add = view.findViewById(R.id.iv_add);

        setValue(getValue());

        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub://减
                subNumber();
                break;

            case R.id.iv_add://加
                addNumebr();
                break;

        }

    }

    private void addNumebr() {
        if (value < maxValue){
            value++;
        }
        setValue(value);
        if (onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }

    private void subNumber() {
        if (value > minValue){
            value--;
        }
        setValue(value);
        if (onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }

    public int getValue() {
        String strValue = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(strValue)){
            value = Integer.parseInt(strValue);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(String.valueOf(value));
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnNumberChangerListener{
        public void onNumberChange(int value);
    }

    public void setOnNumberChangerListener(OnNumberChangerListener onNumberChangerListener) {
        this.onNumberChangerListener = onNumberChangerListener;
    }

}
