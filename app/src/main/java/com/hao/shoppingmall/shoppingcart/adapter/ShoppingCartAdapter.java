package com.hao.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hao.shoppingmall.R;
import com.hao.shoppingmall.home.bean.GoodsBean;
import com.hao.shoppingmall.shoppingcart.utils.CartStorage;
import com.hao.shoppingmall.shoppingcart.view.AddSubViewLayout;
import com.hao.shoppingmall.utils.Constants;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private Context context;
    private List<GoodsBean> data;
    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;
    //完成状态下的删除CheckBox
    private CheckBox cbAll;

    private OnItemClickListener onItemClickListener;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        context = mContext;
        data = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;

        showTotalPrice();
        setListener();
        checkAll();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_shop_cart, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final GoodsBean goodsBean = data.get(i);
        viewHolder.cb_gov.setChecked(goodsBean.isSelected());
        Glide.with(context).load(Constants.BASE_IMAGE_URL + goodsBean.getFigure()).into(viewHolder.iv_gov);
        viewHolder.tv_desc_gov.setText(goodsBean.getName());
        viewHolder.tv_price_gov.setText("￥" + goodsBean.getCover_price());
        viewHolder.addSubViewLayout.setValue(goodsBean.getNumber());

        viewHolder.addSubViewLayout.setOnNumberChangerListener(new AddSubViewLayout.OnNumberChangerListener() {
            @Override
            public void onNumberChange(int value) {
                goodsBean.setNumber(value);
                CartStorage.getInstance(context).updateData(goodsBean);
                notifyItemChanged(viewHolder.getLayoutPosition());
                showTotalPrice();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubViewLayout addSubViewLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            addSubViewLayout = itemView.findViewById(R.id.numberAddSubView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void showTotalPrice(){
        tvShopcartTotal.setText("合计:"+getTotalPrice());
    }

    private String getTotalPrice() {
        double totalprice = 0.0;
        if (data != null && !data.isEmpty()){
            for (GoodsBean goodsBean:data){
                if (goodsBean.isSelected()){
                    totalprice =  totalprice + goodsBean.getNumber() * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return String.valueOf(totalprice);
    }

    public void checkAll(){
        if (data!= null && !data.isEmpty()){
            for (GoodsBean goodsBean:data){
                if (!goodsBean.isSelected()){
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                    return;
                }
            }
            checkboxAll.setChecked(true);
            cbAll.setChecked(true);
        }else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void checkAll_none(boolean isChecked){
        if (data!= null && !data.isEmpty()){
            for (GoodsBean goodsBean:data){
                goodsBean.setSelected(isChecked);
            }
            notifyDataSetChanged();
        }
    }

    private void setListener(){
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                GoodsBean goodsBean = data.get(position);
                goodsBean.setSelected(!goodsBean.isSelected());
                notifyItemChanged(position);
                checkAll();
                showTotalPrice();
            }
        });

        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll_none(checkboxAll.isChecked());
                showTotalPrice();
            }
        });

        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll_none(cbAll.isChecked());
            }
        });
    }

    public void deleteData() {
        if (data != null && !data.isEmpty()){
            for (int i=0; i<data.size(); i++){
                GoodsBean goodsBean = data.get(i);
                if (goodsBean.isSelected()){
                    data.remove(goodsBean);
                    CartStorage.getInstance(context).deleteData(goodsBean);
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }
}
