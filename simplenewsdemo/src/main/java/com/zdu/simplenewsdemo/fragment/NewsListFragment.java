package com.zdu.simplenewsdemo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.adapter.NewsListAdapter;
import com.zdu.simplenewsdemo.bean.NewsBean;
import com.zdu.simplenewsdemo.network.Network;
import com.zdu.simplenewsdemo.ui.NewsDetailsActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 新闻列表
 * Created by duzongning on 2016/8/10.
 */
@SuppressLint("ValidFragment")
public class NewsListFragment extends BaseFragment {
    @Bind(R.id.recycleview)
    RecyclerView mRecycleview;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private int index;
    private Subscription subscribe;
    private String type;
    private String ID;
    private NewsListAdapter mAdapter;
    private LinearLayoutManager manager;
    private boolean mLoadMore;
    private boolean mRefreshMore;
    private ArrayList<NewsBean.NewsEntity> list = new ArrayList<>();

    public NewsListFragment() {
    }

    public NewsListFragment(String type, String ID) {
        this.type = type;
        this.ID = ID;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
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
            loadNews(index);
        }
    }

    /**
     * 加载数据
     *
     * @param index
     */
    private void loadNews(int index) {
        subscribe = Network.getNewsApi()
                .getNewsBean(type, ID, index)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!mLoadMore)
                            mRefresh.setRefreshing(true);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private Observer<NewsBean> observer = new Observer<NewsBean>() {
        @Override
        public void onCompleted() {
            Log.d("tag", "onCompleted");
            mHasLoadedOnce = true;
            mRefresh.setRefreshing(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.d("tag", "error");
            mRefresh.setRefreshing(false);
            mHasLoadedOnce = false;
        }

        @Override
        public void onNext(NewsBean newsBean) {
            int size = list.size();
            switch (ID) {
                case "T1348647909107":
                    list.addAll(newsBean.getT1348647909107());
                    break;
                case "T1348649145984":
                    list.addAll(newsBean.getT1348649145984());
                    break;
                case "T1348654060988":
                    list.addAll(newsBean.getT1348654060988());
                    break;
                default:
                    list.addAll(newsBean.getT1350383429665());
                    break;
            }
            if (size != 0 && list.size() - size < 10) {
                mAdapter.setShowFooter(false);
            }
            if (mLoadMore || mRefreshMore) {
                mLoadMore = false;
                mRefreshMore = false;
                mAdapter.notifyDataSetChanged();
                return;
            }
            mAdapter = new NewsListAdapter(list);
            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//            mRecycleview.setHasFixedSize(true);
            mRecycleview.setAdapter(mAdapter);
            manager = new LinearLayoutManager(getActivity());
            mRecycleview.setLayoutManager(manager);
            mAdapter.setShowFooter(true);
            mRecycleview.addOnScrollListener(mOnScrollListener);
            mRefresh.setOnRefreshListener(mOnRefreshListener);
            mAdapter.setOnItemClickListener(mOnItemClickListener);
            mHasNet = true;
        }
    };

    private NewsListAdapter.ItemClickListener mOnItemClickListener = new NewsListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsBean.NewsEntity newsEntity = list.get(position);
            Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
            intent.putExtra("newsEntity", newsEntity);
            //TODO 以共享元素为基准开启新的Activity(会崩溃)
//            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.ivNews), getString(R.string.image));
//            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
            startActivity(intent);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            index = 0;
            mRefreshMore = true;
            if (list != null) {
                list.clear();
            }
            loadNews(index);
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = manager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount() && mAdapter.isShowFooter()) {//初始化状态&&最后一个列表
                //加载更多
                mLoadMore = true;
                index += 10;
                loadNews(index);
            }
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
