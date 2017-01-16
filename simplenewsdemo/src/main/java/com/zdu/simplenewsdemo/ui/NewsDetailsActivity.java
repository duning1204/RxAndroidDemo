package com.zdu.simplenewsdemo.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.bean.NewsBean;
import com.zdu.simplenewsdemo.bean.NewsDetailsBean;
import com.zdu.simplenewsdemo.network.Network;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NewsDetailsActivity extends AppCompatActivity {

    @Bind(R.id.header_iv)
    ImageView mHeaderIv;
    @Bind(R.id.header_toolbar)
    Toolbar mHeaderToolbar;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.html_tv)
    WebView mHtmlTv;

    private Subscription subscribe;

    private NewsBean.NewsEntity newsEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        setSupportActionBar(mHeaderToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//toolbar上向左的小箭头可以点击
        mHeaderToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mHeaderToolbar.setOnMenuItemClickListener(mOnMenuItem);
        mHeaderToolbar.inflateMenu(R.menu.news_details_menu);
        newsEntity = getIntent().getParcelableExtra("newsEntity");
        mCollapsingToolbarLayout.setTitle(newsEntity.getTitle());
        Glide.with(this).load(newsEntity.getImgsrc()).crossFade().into(mHeaderIv);
        initWebView();
        subscribe = getSubscribe();

        initShareSDK();
    }

    private void initWebView() {
        WebSettings settings = mHtmlTv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
    }


    /**
     * ShareSDK
     */
    private OnekeyShare oks;

    private void initShareSDK() {
        ShareSDK.initSDK(this);
        oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(getString(R.string.share));
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(getString(R.string.share));
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.share));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(getString(R.string.share));
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
    }


    private Toolbar.OnMenuItemClickListener mOnMenuItem = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.share:
//                    Snackbar.make(mHeaderToolbar, "分享", Snackbar.LENGTH_SHORT).show();
                    oks.show(getApplicationContext());
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu, menu);
        return true;
    }

    public Subscription getSubscribe() {
        return Network.getNewsApiForString()
                .getNewsDetails(newsEntity.getDocid())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String json = jsonObject.optString(newsEntity.getDocid());
                            NewsDetailsBean newsDetailsBean = new Gson().fromJson(json, NewsDetailsBean.class);
                            return newsDetailsBean.getBody();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mHtmlTv.loadDataWithBaseURL(null, s, "text/html", "UTF-8", null);
                    }
                });
    }

    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

}
