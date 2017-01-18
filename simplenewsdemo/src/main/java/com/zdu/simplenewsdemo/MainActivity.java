package com.zdu.simplenewsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
import com.zdu.simplenewsdemo.fragment.AboutFragment;
import com.zdu.simplenewsdemo.fragment.BaseFragment;
import com.zdu.simplenewsdemo.fragment.IMListFragment;
import com.zdu.simplenewsdemo.fragment.NewsFragment;
import com.zdu.simplenewsdemo.fragment.PicFragment;
import com.zdu.simplenewsdemo.fragment.WeatherFragment;
import com.zdu.simplenewsdemo.ui.LoginActivity;
import com.zdu.simplenewsdemo.utils.CommonUtils;
import com.zdu.simplenewsdemo.utils.QQLoginListener;
import com.zdu.simplenewsdemo.utils.SpUtils;
import com.zdu.simplenewsdemo.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewpager)
    NoScrollViewPager mViewpager;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;
    @Bind(R.id.navagation_view)
    NavigationView mNavigationView;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    private ArrayList<BaseFragment> mList;

    private QQLoginListener mQQLoginListener;
    private String appId = CommonUtils.QQ_APPID;
    private Tencent mTencent;
    private String token;//im聊天
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.news));//放在 setSupportActionBar(mToolbar)之前调用，否则不起作用
        setSupportActionBar(mToolbar);
        initDrawerToggle();
        initViewPager();
        navigationHeader();
        mNavigationView.setNavigationItemSelectedListener(mNavigationItem);
        mTencent = Tencent.createInstance(appId, getApplicationContext());
        token = SpUtils.getInstance(this).get("token", null);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                manager.scrollToPosition(0);
            }
        });

    }

    /**
     * 在Toolbar上初始化侧滑按钮
     */
    private void initDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbar, R.string.open, R.string.close);
        toggle.syncState();
        mDrawerlayout.addDrawerListener(toggle);
    }


    /**
     * 侧滑头部
     */
    private void navigationHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.profile_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, getString(R.string.click_head_pic), Toast.LENGTH_SHORT).show();
//                Snackbar.make(mNavigationView, getString(R.string.click_head_pic), Snackbar.LENGTH_SHORT).show();
                if (!mTencent.isSessionValid()) {
                    mQQLoginListener = new QQLoginListener(mTencent, mNavigationView);
                    mTencent.login(MainActivity.this, "get_simple_userinfo", mQQLoginListener);
//                    mTencent.login(MainActivity.this, "all", mQQLoginListener);
//                    Snackbar.make(mNavigationView, getString(R.string.login_success), Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(mNavigationView, getString(R.string.loginned), Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * qq第三方登录，必须得加以下代码，否则不回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mQQLoginListener);
        if (requestCode == Constants.REQUEST_API && resultCode == Constants.REQUEST_LOGIN) {
            Tencent.handleResultData(data, mQQLoginListener);
            Snackbar.make(mNavigationView, mNavigationView.getContext().getString(R.string.login_success), Snackbar.LENGTH_SHORT).show();
        }
        if (requestCode == CommonUtils.FINISH_ACTIVITY_LOGIN) {
            mViewpager.setCurrentItem(3);
            mToolbar.setTitle(getString(R.string.im));
        }
    }

    /**
     * 侧滑菜单各选项的点击事件
     */
    private NavigationView.OnNavigationItemSelectedListener mNavigationItem = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            item.setChecked(true);
            mDrawerlayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.navigation_item_news:
                    mViewpager.setCurrentItem(0);
                    mToolbar.setTitle(getString(R.string.news));
                    break;
                case R.id.navigation_item_images:
                    mViewpager.setCurrentItem(1);
                    mToolbar.setTitle(getString(R.string.pic));
                    break;
                case R.id.navigation_item_weather:
                    mViewpager.setCurrentItem(2);
                    mToolbar.setTitle(getString(R.string.weather));
                    break;
                case R.id.navigation_item_im:
                    //TODO 聊天
                    if (!CommonUtils.IM_SUCCESS) {
                        startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), CommonUtils.FINISH_ACTIVITY_LOGIN);
                    } else {
                        mViewpager.setCurrentItem(3);
                        mToolbar.setTitle(getString(R.string.im));
                    }
                    break;
                case R.id.navigation_item_about:
                    mViewpager.setCurrentItem(4);
                    mToolbar.setTitle(getString(R.string.about));
                    break;
            }
            return true;
        }
    };

    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new NewsFragment());
        mList.add(new PicFragment());
        mList.add(new WeatherFragment());
        mList.add(new IMListFragment());
        mList.add(new AboutFragment());
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(5);
        mViewpager.setPagingEnabled(false);
    }

    class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    private long exitTime;

    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(mNavigationView)) {
            mDrawerlayout.closeDrawers();
            return;
        }
        if (exitTime == 0 || System.currentTimeMillis() - exitTime >= 2000) {
            Toast.makeText(MainActivity.this, getString(R.string.exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - exitTime < 2000) {
            exitTime = 0;
            super.onBackPressed();
        }

    }


    public void showFAB(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mFab.setVisibility(View.VISIBLE);
        mFab.attachToRecyclerView(recyclerView);
    }

    public void hideFAB() {
        mFab.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mTencent.logout(this);
    }
}
