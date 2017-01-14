package com.zdu.simplenewsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.adapter.PicAdapter;
import com.zdu.simplenewsdemo.bean.ImageBean;
import com.zdu.simplenewsdemo.network.Network;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 图片
 * Created by duzongning on 2016/9/5.
 */
public class PicFragment extends BaseFragment {
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
    private Subscription subscribe;
    private ArrayList<ImageBean> mList;
    private boolean mRefresh;
    private PicAdapter mAdapter;
    private LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
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
            loadPic();
            mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mRefresh = true;
                    if (mList != null) {
                        mList.clear();
                    }
                    loadPic();
                }
            });
        }
    }

    private void loadPic() {
        subscribe = Network.getPicApi()
                .getPicData()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSwipeRefreshWidget.setRefreshing(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Observer<ArrayList<ImageBean>> observer = new Observer<ArrayList<ImageBean>>() {
        @Override
        public void onCompleted() {
            mHasLoadedOnce = true;
            mHasNet = true;
            mSwipeRefreshWidget.setRefreshing(false);
        }

        @Override
        public void onError(Throwable e) {
            mHasNet = false;
            mHasLoadedOnce = false;
            mSwipeRefreshWidget.setRefreshing(false);
        }

        @Override
        public void onNext(ArrayList<ImageBean> imageBeen) {
            if (mRefresh) {
                mList.addAll(imageBeen);
                mAdapter.notifyDataSetChanged();
                mRefresh = false;
                return;
            }
            mList = new ArrayList<>();
            mList.addAll(imageBeen);
            mAdapter = new PicAdapter(mList);
            manager = new LinearLayoutManager(getActivity());
            mRecycleView.setLayoutManager(manager);
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.addOnScrollListener(mOnScroll);
        }
    };

    private RecyclerView.OnScrollListener mOnScroll = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {//最后一条
                Snackbar.make(mRecycleView, getString(R.string.pic_hint), Snackbar.LENGTH_SHORT).setAction(getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecycleView.scrollToPosition(0);//直接定位到第一条
//                        mRecycleView.smoothScrollToPosition(0);//滑到第一条
                    }
                }).show();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = manager.findLastVisibleItemPosition();
        }
    };

    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        unSubscribe();
    }
}
