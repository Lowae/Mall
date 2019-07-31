package com.hao.shoppingmall.goods;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hao.shoppingmall.R;
import com.hao.shoppingmall.goods.adapter.ExpandableListViewAdapter;
import com.hao.shoppingmall.home.adapter.GoodsListAdapter;
import com.hao.shoppingmall.home.bean.GoodsBean;
import com.hao.shoppingmall.home.bean.TypeListBean;
import com.hao.shoppingmall.home.utils.SpaceItemDecoration;
import com.hao.shoppingmall.utils.Constants;
import com.hao.shoppingmall.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 商品列表页面
public class GoodsListActivity extends Activity {

    public static final String GOODS_BEAN = "goodsBean";


    @BindView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @BindView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @BindView(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @BindView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @BindView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @BindView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.ib_drawer_layout_back)
    ImageButton ibDrawerLayoutBack;
    @BindView(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @BindView(R.id.ib_drawer_layout_confirm)
    TextView ibDrawerLayoutConfirm;
    @BindView(R.id.rb_select_hot)
    RadioButton rbSelectHot;
    @BindView(R.id.rb_select_new)
    RadioButton rbSelectNew;
    @BindView(R.id.rg_select)
    RadioGroup rgSelect;
    @BindView(R.id.tv_drawer_price)
    TextView tvDrawerPrice;
    @BindView(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @BindView(R.id.tv_drawer_recommend)
    TextView tvDrawerRecommend;
    @BindView(R.id.rl_select_recommend_theme)
    RelativeLayout rlSelectRecommendTheme;
    @BindView(R.id.tv_drawer_type)
    TextView tvDrawerType;
    @BindView(R.id.rl_select_type)
    RelativeLayout rlSelectType;
    @BindView(R.id.btn_select_all)
    Button btnSelectAll;
    @BindView(R.id.ll_select_root)
    LinearLayout llSelectRoot;
    @BindView(R.id.btn_drawer_layout_cancel)
    Button btnDrawerLayoutCancel;
    @BindView(R.id.btn_drawer_layout_confirm)
    Button btnDrawerLayoutConfirm;
    @BindView(R.id.iv_price_no_limit)
    ImageView ivPriceNoLimit;
    @BindView(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @BindView(R.id.iv_price_0_15)
    ImageView ivPrice015;
    @BindView(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @BindView(R.id.iv_price_15_30)
    ImageView ivPrice1530;
    @BindView(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @BindView(R.id.iv_price_30_50)
    ImageView ivPrice3050;
    @BindView(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @BindView(R.id.iv_price_50_70)
    ImageView ivPrice5070;
    @BindView(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @BindView(R.id.iv_price_70_100)
    ImageView ivPrice70100;
    @BindView(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @BindView(R.id.iv_price_100)
    ImageView ivPrice100;
    @BindView(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @BindView(R.id.et_price_start)
    EditText etPriceStart;
    @BindView(R.id.v_price_line)
    View vPriceLine;
    @BindView(R.id.et_price_end)
    EditText etPriceEnd;
    @BindView(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @BindView(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @BindView(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @BindView(R.id.iv_theme_all)
    ImageView ivThemeAll;
    @BindView(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @BindView(R.id.iv_theme_note)
    ImageView ivThemeNote;
    @BindView(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @BindView(R.id.iv_theme_funko)
    ImageView ivThemeFunko;
    @BindView(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @BindView(R.id.iv_theme_gsc)
    ImageView ivThemeGsc;
    @BindView(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @BindView(R.id.iv_theme_origin)
    ImageView ivThemeOrigin;
    @BindView(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @BindView(R.id.iv_theme_sword)
    ImageView ivThemeSword;
    @BindView(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @BindView(R.id.iv_theme_food)
    ImageView ivThemeFood;
    @BindView(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @BindView(R.id.iv_theme_moon)
    ImageView ivThemeMoon;
    @BindView(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @BindView(R.id.iv_theme_quanzhi)
    ImageView ivThemeQuanzhi;
    @BindView(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @BindView(R.id.iv_theme_gress)
    ImageView ivThemeGress;
    @BindView(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @BindView(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @BindView(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @BindView(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.ll_type_root)
    LinearLayout llTypeRoot;
    @BindView(R.id.dl_left)
    DrawerLayout dlLeft;

    private int childP;
    private int groupP;

    private int click_count = 0;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter adapter;
    private GoodsListAdapter goodsListAdapter;
    private ExpandableListView listView;
    private List<TypeListBean.ResultBean.PageDataBean> page_data;

    private int position;
    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        getDataFromNet();
        llSelectRoot.setVisibility(View.VISIBLE);
        ibDrawerLayoutBack.setVisibility(View.VISIBLE);
        showSelectorLayout();

    }

    @OnClick({R.id.ib_goods_list_back, R.id.tv_goods_list_search, R.id.ib_goods_list_home, R.id.tv_goods_list_sort, R.id.ll_goods_list_price, R.id.tv_goods_list_select, R.id.ib_drawer_layout_back, R.id.rl_select_price, R.id.rl_select_recommend_theme, R.id.rl_select_type, R.id.btn_drawer_layout_cancel, R.id.btn_drawer_layout_confirm, R.id.rl_price_30_50, R.id.btn_drawer_theme_cancel, R.id.btn_drawer_theme_confirm, R.id.rl_theme_note, R.id.btn_drawer_type_cancel, R.id.btn_drawer_type_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
                Toast.makeText(GoodsListActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_goods_list_home:
                Constants.isBackHome = true;
                finish();
                break;
            case R.id.tv_goods_list_sort:
                //综合排序点击事件
                click_count = 0;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
                break;
            case R.id.ll_goods_list_price:
                //价格点击事件
                click_count++;
                //综合排序变为默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                //价格红
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                if (click_count % 2 == 1) {
                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                } else {
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }
                break;
            case R.id.tv_goods_list_select:
                //筛选的点击事件
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                dlLeft.openDrawer(Gravity.END);
                break;
            case R.id.ib_drawer_layout_back:
                dlLeft.closeDrawers();
                break;
            case R.id.rl_select_price:
                //价格筛选的页面
                llPriceRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);
                showPriceLayout();
                break;
            case R.id.rl_select_recommend_theme:
                llThemeRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);
                showThemeLayout();
                break;
            case R.id.rl_select_type:
                llTypeRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);
                showTypeLayout();
                break;
            case R.id.btn_drawer_layout_cancel:
                Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();

                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_layout_confirm:
                break;
            case R.id.rl_price_30_50:
                break;
            case R.id.btn_drawer_theme_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_theme_confirm:
                break;
            case R.id.rl_theme_note:
                break;
            case R.id.btn_drawer_type_cancel:
                Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_type_confirm:
                break;
        }
    }


    private void getDataFromNet() {
        OkHttpUtils.sendRequest(OkHttpUtils.createGetRequest(urls[position]), new OkHttpUtils.JsonCallback(new OkHttpUtils.DisposeDataHandle<>(new OkHttpUtils.DisposeDataListener<TypeListBean>() {
            @Override
            public void onSuccess(TypeListBean typeListBean) {
                page_data = typeListBean.getResult().getPage_data();

                recyclerview.setLayoutManager(new GridLayoutManager(GoodsListActivity.this, 2));
                goodsListAdapter = new GoodsListAdapter(GoodsListActivity.this, page_data);
                recyclerview.addItemDecoration(new SpaceItemDecoration(10));
                recyclerview.setAdapter(goodsListAdapter);
                goodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
                    @Override
                    public void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data) {
                        String name = data.getName();
                        String cover_price = data.getCover_price();
                        String figure = data.getFigure();
                        String product_id = data.getProduct_id();

                        GoodsBean goodsBean = new GoodsBean(cover_price, figure, name, product_id);
                        Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                        intent.putExtra(GOODS_BEAN, goodsBean);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {

            }
        }, TypeListBean.class)));
    }

    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<>(Arrays.asList(c));
        child.add(list);
    }

    //筛选页面
    private void showSelectorLayout() {
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //价格页面
    private void showPriceLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //主题页面
    private void showThemeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
        adapter = new ExpandableListViewAdapter(this, group, child);
        listView.setAdapter(adapter);
    }

    private void initExpandableListView() {
        group = new ArrayList<>();
        child = new ArrayList<>();
        //去掉默认箭头
        listView.setGroupIndicator(null);
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});

        // 这里是控制如果列表没有孩子菜单不展开的效果
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                // isEmpty没有
                return child.get(groupPosition).isEmpty();
            }
        });
    }
}
