package com.zdu.simplenewsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.adapter.NewsPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻
 * Created by duzongning on 2016/9/5.
 */
public class NewsFragment extends BaseFragment {
    @Bind(R.id.tab_layout)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    /**
     * 标志位，标志已经初始化完成
     * onCreateView()方法内设置为true
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     * 网络请求完成设置数据成功后设置为true
     */
    private boolean mHasLoadedOnce;
    /**
     * 是否联网成功
     */
    private boolean mHasNet;

    private ArrayList<NewsListFragment> mList;

    private NewsPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        return view;
    }

    @Override
    protected void lazyLoad() {
        //判断是否 初始化完成，是否处于显示状态，是否已经加载完成过数据
        if (isPrepared && isVisible && mHasLoadedOnce && mHasNet) {
            return;
        }
        if (isPrepared) {
            initViewPager();
            mTabs.addTab(mTabs.newTab().setText(getString(R.string.topLine)));
            mTabs.addTab(mTabs.newTab().setText(getString(R.string.nba)));
            mTabs.addTab(mTabs.newTab().setText(getString(R.string.car)));
            mTabs.addTab(mTabs.newTab().setText(getString(R.string.joke)));
            mTabs.setupWithViewPager(mViewpager);
            mHasLoadedOnce = true;
            mHasNet = true;
        }
    }

    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new NewsListFragment("headline", "T1348647909107"));
        mList.add(new NewsListFragment("list", "T1348649145984"));
        mList.add(new NewsListFragment("list", "T1348654060988"));
        mList.add(new NewsListFragment("list", "T1350383429665"));
        adapter = new NewsPagerAdapter(getChildFragmentManager(), mList);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
