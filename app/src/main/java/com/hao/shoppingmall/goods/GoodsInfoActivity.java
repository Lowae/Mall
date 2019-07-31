package com.hao.shoppingmall.goods;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hao.shoppingmall.R;
import com.hao.shoppingmall.goods.adapter.GoodsPagerAdapter;
import com.hao.shoppingmall.goods.fragment.CommentFragment;
import com.hao.shoppingmall.goods.fragment.ContentDetailFragment;
import com.hao.shoppingmall.goods.fragment.GoodsInfoFragment;
import com.hao.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.hao.shoppingmall.home.bean.GoodsBean;
import com.hao.shoppingmall.home.utils.VirtualkeyboardHeight;
import com.hao.shoppingmall.shoppingcart.utils.CartStorage;
import com.hao.shoppingmall.shoppingcart.view.AddSubViewLayout;
import com.hao.shoppingmall.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsInfoActivity extends AppCompatActivity {

    public static final String ARG_GOODSINFO = "GoodsBean";

    @BindView(R.id.goods_tabs)
    TabLayout goodsTabs;
    @BindView(R.id.goods_toolbar)
    Toolbar goodsToolbar;
    @BindView(R.id.goods_viewpager)
    ViewPager goodsViewpager;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;

    private GoodsPagerAdapter goodsPagerAdapter;
    private GoodsBean goodsBean;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        setSupportActionBar(goodsToolbar);
        goodsToolbar.setNavigationIcon(R.drawable.ic_menu_back);

        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeFragmentAdapter.GOODS_BEAN);
        if (goodsBean != null) {
            initDatas();
            Log.e("TAG", goodsBean.toString());
            goodsPagerAdapter = new GoodsPagerAdapter(getSupportFragmentManager(), fragmentList);
            goodsViewpager.setOffscreenPageLimit(3);
            goodsViewpager.setAdapter(goodsPagerAdapter);
            goodsTabs.setupWithViewPager(goodsViewpager);
        }

    }

    private void initDatas() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_GOODSINFO, goodsBean);
        GoodsInfoFragment goodsInfoFragment = GoodsInfoFragment.newInstance();
        goodsInfoFragment.setArguments(bundle);
        fragmentList.add(goodsInfoFragment);
        fragmentList.add(ContentDetailFragment.newInstance());
        fragmentList.add(CommentFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods, menu);
        goodsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goodsToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_more:
                        Log.e("TAG", "action_more");

                        View view = getLayoutInflater().inflate(R.layout.goods_pop_menu, null);
                        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.showAsDropDown(goodsToolbar);
                        break;
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_good_info_callcenter:
                break;
            case R.id.tv_good_info_collection:
                break;
            case R.id.tv_good_info_cart:
                break;
            case R.id.btn_good_info_addcart:
                showPopwindow();
//                CartStorage.getInstance(this).addData(goodsBean);
                break;
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {

        // 1 利用layoutInflater获得View
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        window.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        ImageView iv_goodinfo_photo = view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = view.findViewById(R.id.tv_goodinfo_price);
        AddSubViewLayout nas_goodinfo_num = view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_IMAGE_URL + goodsBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(goodsBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(goodsBean.getCover_price());


        nas_goodinfo_num.setOnNumberChangerListener(new AddSubViewLayout.OnNumberChangerListener() {

            @Override
            public void onNumberChange(int value) {
                goodsBean.setNumber(value);

            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                //添加购物车
                CartStorage.getInstance(getApplicationContext()).addData(goodsBean);
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(llGoodsRoot, Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));

    }
}
