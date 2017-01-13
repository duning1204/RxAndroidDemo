package com.zdu.simplenewsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdu.simplenewsdemo.R;

/**
 * 关于我们
 * Created by duzongning on 2016/9/5.
 */
public class AboutFragment extends BaseFragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }

    @Override
    protected void lazyLoad() {
        //判断是否 初始化完成，是否处于显示状态，是否已经加载完成过数据
        if (isPrepared && isVisible && mHasLoadedOnce && mHasNet) {
            return;
        }
        if (isPrepared) {

        }
    }
}
