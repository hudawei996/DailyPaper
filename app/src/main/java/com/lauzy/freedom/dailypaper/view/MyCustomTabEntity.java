package com.lauzy.freedom.dailypaper.view;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.lauzy.freedom.dailypaper.app.MyApp;

/**
 * Created by Lauzy on 2016/11/1.
 */

public class MyCustomTabEntity implements CustomTabEntity {

    private String mTabTitle;
    private int mTabSelectedIcon;
    private int mTabUnSelectedIcon;

    public MyCustomTabEntity(String tabTitle, int tabSelectedIcon, int tabUnSelectedIcon) {
        mTabTitle = tabTitle;
        mTabSelectedIcon = tabSelectedIcon;
        mTabUnSelectedIcon = tabUnSelectedIcon;
    }

    public MyCustomTabEntity(int tabTitleId, int tabSelectedIcon, int tabUnSelectedIcon) {
        mTabTitle = MyApp.mContext.getResources().getString(tabTitleId);
        mTabSelectedIcon = tabSelectedIcon;
        mTabUnSelectedIcon = tabUnSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return mTabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mTabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mTabUnSelectedIcon;
    }
}
