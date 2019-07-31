package com.hao.shoppingmall.goods.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hao.shoppingmall.R;
import com.hao.shoppingmall.goods.GoodsInfoActivity;
import com.hao.shoppingmall.goods.page.Page;
import com.hao.shoppingmall.goods.page.PageBehavior;
import com.hao.shoppingmall.goods.page.PageContainer;
import com.hao.shoppingmall.home.bean.GoodsBean;
import com.hao.shoppingmall.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsInfoFragment extends Fragment {


    @BindView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_goods_info_more)
    WebView wbGoodsInfoMore;
    Unbinder unbinder;
    @BindView(R.id.pageOne)
    Page pageOne;
    @BindView(R.id.page_container)
    PageContainer container;

    private GoodsBean goodsBean;

    public GoodsInfoFragment() {
        // Required empty public constructor
    }

    private static GoodsInfoFragment fragment = null;

    public static GoodsInfoFragment newInstance() {
        if (fragment == null) {
            fragment = new GoodsInfoFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        goodsBean = (GoodsBean) getArguments().getSerializable(GoodsInfoActivity.ARG_GOODSINFO);
        Log.e("Goods_breeeaab : ", goodsBean.toString());

        Glide.with(getContext()).load(Constants.BASE_IMAGE_URL + goodsBean.getFigure()).into(ivGoodInfoImage);
        tvGoodInfoName.setText(goodsBean.getName());
        tvGoodInfoPrice.setText("￥" + goodsBean.getCover_price());

        setWebViewData(goodsBean.getProduct_id());

        PageContainer pageContainer = container;
        pageContainer.setOnPageChanged(new PageBehavior.OnPageChanged() {
            @Override
            public void toTop() {

            }

            @Override
            public void toBottom() {

            }
        });
    }

    private void setWebViewData(String product_id) {
        if(product_id != null){
            wbGoodsInfoMore.loadUrl("http://www.baidu.com");
            //设置支持JavaScript
            WebSettings webSettings = wbGoodsInfoMore.getSettings();
            webSettings.setUseWideViewPort(true);//支持双击页面变大变小
            webSettings.setJavaScriptEnabled(true);//设置支持JavaScript
            //优先使用缓存
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wbGoodsInfoMore.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.loadUrl(request.getUrl().toString());
//                    }
//                    return true;
//                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
