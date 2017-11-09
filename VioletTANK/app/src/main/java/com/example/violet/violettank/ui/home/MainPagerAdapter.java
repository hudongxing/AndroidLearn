package com.example.violet.violettank.ui.home;

import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.violet.violettank.ui.base.BaseFragment;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class MainPagerAdapter extends FragmentPagerAdapter{
    private String[] mTitles;
    private BaseFragment[] mFragments;
    public MainPagerAdapter(FragmentManager fm, String[] titles, BaseFragment[] fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        if (mTitles == null) return 0;
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
