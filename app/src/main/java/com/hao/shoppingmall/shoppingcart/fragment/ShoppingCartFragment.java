package com.hao.shoppingmall.shoppingcart.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.base.BaseFragment;
import com.hao.shoppingmall.home.bean.GoodsBean;
import com.hao.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.hao.shoppingmall.shoppingcart.utils.CartStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    Unbinder unbinder;

    private ShoppingCartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    public View initView() {
        Log.e(TAG, "购物车视图初始化");
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        if (view != null) {
            unbinder = ButterKnife.bind(this, view);
        }
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        showData();
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_check_out, R.id.btn_delete, R.id.btn_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_out:
                break;
            case R.id.btn_delete:
                adapter.deleteData();
                adapter.checkAll();
                if (adapter.getItemCount() == 0){
                    emptyShoppingCart();
                }
                break;
            case R.id.btn_collection:
                break;
        }
    }

    private void showData() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance(getContext()).getAllData();


        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            llCheckAll.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llEmptyShopcart.setVisibility(View.GONE);
            adapter = new ShoppingCartAdapter(mContext, goodsBeanList, tvShopcartTotal, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        }else {
            emptyShoppingCart();
        }
    }

    private void emptyShoppingCart() {
        llEmptyShopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }

    private void initListener() {
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) v.getTag();
                if (action == ACTION_EDIT) {
                    //切换为完成状态
                    showDelete();
                } else {
                    //切换成编辑状态
                    hideDelete();
                }
            }
        });
    }

    private void hideDelete() {
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        if (adapter != null){
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void showDelete() {
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.checkAll();
        }
        llDelete.setVisibility(View.VISIBLE);
        llCheckAll.setVisibility(View.GONE);
    }
}
