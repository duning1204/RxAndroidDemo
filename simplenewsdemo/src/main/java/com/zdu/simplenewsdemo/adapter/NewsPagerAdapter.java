package com.zdu.simplenewsdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.fragment.NewsListFragment;

import java.util.ArrayList;

/**
 * 新闻界面
 * Created by duzongning on 2016/8/10.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<NewsListFragment> mList;

    public NewsPagerAdapter(FragmentManager fm, ArrayList<NewsListFragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mList.get(0).getActivity().getString(R.string.topLine);
            case 1:

                return mList.get(1).getActivity().getString(R.string.nba);
            case 2:

                return mList.get(2).getActivity().getString(R.string.car);
            default:

                return mList.get(3).getActivity().getString(R.string.joke);
        }

    }
}
