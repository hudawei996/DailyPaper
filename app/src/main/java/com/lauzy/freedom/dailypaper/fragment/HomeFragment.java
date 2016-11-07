package com.lauzy.freedom.dailypaper.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.adapter.HomeAdapter;
import com.lauzy.freedom.dailypaper.model.ZHThemeList;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.utils.Contants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SlidingTabLayout mTabLayoutHome;
    private ViewPager mViewPagerHome;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private ArrayList<String> mTitleList = new ArrayList<>();
    private Handler mThemeListHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Contants.ZHTHEME_LIST_SUCCESS:
                    ZHThemeList themeList = (ZHThemeList) msg.obj;
                    if (themeList!=null) {
                        List<ZHThemeList.OthersBean> others = themeList.getOthers();

                        //默认添加首页
                        mTitleList.add(getResources().getString(R.string.txt_title_home));
                        mFragments.add(HomePageListFragment.newInstance(getResources().getString(R.string.txt_title_home), ""));

                        for (ZHThemeList.OthersBean other : others) {
                            String name = other.getName();
                            mTitleList.add(name);
                            mFragments.add(HomeItemFragment.newInstance(other.getName(), other.getId()));
                        }

                        mViewPagerHome.setAdapter(new HomeAdapter(getChildFragmentManager(), mFragments, mTitleList));
                        mViewPagerHome.setOffscreenPageLimit(3);
                        mTabLayoutHome.setViewPager(mViewPagerHome);
                    }
                    break;
            }
        }
    };

    private void initView(View view) {
        mTabLayoutHome = ((SlidingTabLayout) view.findViewById(R.id.tablayout_home));
        mViewPagerHome = ((ViewPager) view.findViewById(R.id.viewpager_home));
        RetrofitUtils.getZHThemeListData(mThemeListHandler, Contants.ZHTHEME_LIST_SUCCESS);
    }
}
