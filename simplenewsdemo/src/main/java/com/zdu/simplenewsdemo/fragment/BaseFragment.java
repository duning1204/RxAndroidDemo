package com.zdu.simplenewsdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 屏蔽ViewPager+Fragment的预加载
 * @author zdu
 *
 */
public abstract class BaseFragment extends Fragment {
	/**
	 *  Fragment当前状态是否可见 
	 */
	protected boolean isVisible;

	/**
	 * 判断Fragment中的视图存在时候存在，判断该Fragment时候已经正在前台显示    通过这两个判断，就可以知道什么时候去加载数据了
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	/**
	 * 与上面的方法同时调用，可以防止预加载   这里的预加载只是指防止预加载数据
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();
	}

	/**
	 * 不可见
	 */
	protected void onInvisible() {

	}

	/** 
	 * 延迟加载   
	 * 子类必须重写此方法   可实现数据延迟加载  不需要再次调用
	 */
	protected abstract void lazyLoad();


}
