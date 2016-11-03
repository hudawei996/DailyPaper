package com.lauzy.freedom.dailypaper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lauzy on 2016/11/1.
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitleList;

    public HomeAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList,ArrayList<String> titleList) {
        super(fm);
        mFragments = fragmentArrayList;
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
