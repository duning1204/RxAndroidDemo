package com.zdu.simplenewsdemo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.utils.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by duzongning on 2016/9/27.
 */
public class IMListFragment extends BaseFragment {
    @Bind(R.id.conversationlist)
    FrameLayout mConversationlist;
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
        View view = inflater.inflate(R.layout.fragment_im, container, false);
        isPrepared = true;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void lazyLoad() {
        //判断是否 初始化完成，是否处于显示状态，是否已经加载完成过数据
        if (isPrepared && isVisible && mHasLoadedOnce && mHasNet) {
            return;
        }
        if (isPrepared) {
            //成功连接im服务器
            if (CommonUtils.IM_SUCCESS) {
                mHasNet = true;
                ConversationListFragment fragment = new ConversationListFragment();
                Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                        .build();
                fragment.setUri(uri);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.conversationlist, fragment);
                transaction.commit();
                mHasLoadedOnce = true;
            } else {
                Snackbar.make(mConversationlist, getString(R.string.connect_fail), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
