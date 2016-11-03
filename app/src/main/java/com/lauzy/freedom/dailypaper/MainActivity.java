package com.lauzy.freedom.dailypaper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.lauzy.freedom.dailypaper.fragment.CommunityFragment;
import com.lauzy.freedom.dailypaper.fragment.IdeaFragment;
import com.lauzy.freedom.dailypaper.fragment.FindFragment;
import com.lauzy.freedom.dailypaper.fragment.HomeFragment;
import com.lauzy.freedom.dailypaper.view.MyCustomTabEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments;
    private GestureDetector mGestureDetector;
    private Toolbar mToolbar;
    private CommonTabLayout mCommonTabLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        initTabs();
//        initGesture();
        initDrawer();
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initGesture() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.e("TAG", "onShowPress: ");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("TAG", "onSingleTapUp: ");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("TAG", "onScroll: ");
                if (distanceY > 0) {
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator animTab = ObjectAnimator.ofFloat(mCommonTabLayout, "TranslationY", 0, mCommonTabLayout.getHeight());
                    ObjectAnimator animToolbar = ObjectAnimator.ofFloat(mToolbar, "TranslationY", 0, -mToolbar.getHeight());
                    set.setDuration(200);
                    set.play(animTab).with(animToolbar);
                    set.start();
                } else if (distanceY < 0) {
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator animTab = ObjectAnimator.ofFloat(mCommonTabLayout, "TranslationY", mCommonTabLayout.getHeight(), 0);
                    ObjectAnimator animToolbar = ObjectAnimator.ofFloat(mToolbar, "TranslationY", -mToolbar.getHeight(), 0);
                    set.setDuration(200);
                    set.play(animTab).with(animToolbar);
                    set.start();
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("TAG", "onLongPress: ");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("TAG", "onFling: ");
                return false;
            }
        });
    }

    private void initFragments() {

        mFragments = new ArrayList<>();

        HomeFragment homeFragment = HomeFragment.newInstance("", "");
        FindFragment findFragment = FindFragment.newInstance("", "");
        CommunityFragment communityFragment = CommunityFragment.newInstance("", "");
        IdeaFragment ideaFragment = IdeaFragment.newInstance("", "");

        mFragments.add(homeFragment);
        mFragments.add(findFragment);
        mFragments.add(communityFragment);
        mFragments.add(ideaFragment);

    }

    private void initTabs() {

        mCommonTabLayout = (CommonTabLayout) findViewById(R.id.tablayout_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        mToolbar.setNavigationIcon(R.mipmap.icon_navigation);
        setSupportActionBar(mToolbar);

        ArrayList<CustomTabEntity> tabs = new ArrayList<>();

        CustomTabEntity tabHome = new MyCustomTabEntity(getResources().getString(R.string.tab_main_home),
                R.mipmap.icon_tab_main_home_selected_64, R.mipmap.icon_tab_main_home_unselected_64);

        CustomTabEntity tabFind = new MyCustomTabEntity(getResources().getString(R.string.tab_main_find),
                R.mipmap.icon_tab_main_find_selected_64, R.mipmap.icon_tab_main_find_unselected_64);

        CustomTabEntity tabSocial = new MyCustomTabEntity(getResources().getString(R.string.tab_main_community),
                R.mipmap.icon_tab_main_social_selected_64, R.mipmap.icon_tab_main_social_unselected_64);

        CustomTabEntity tabCreate = new MyCustomTabEntity(getResources().getString(R.string.tab_main_idea),
                R.mipmap.icon_tab_main_create_selected_64, R.mipmap.icon_tab_main_create_unselected_64);

        tabs.add(tabHome);
        tabs.add(tabFind);
        tabs.add(tabSocial);
        tabs.add(tabCreate);

        mCommonTabLayout.setTabData(tabs, this, R.id.fragment_main, mFragments);

    }

   /* *//**
     * 复写activity的ontouchevent方法获取触屏事件
     * 将event传递给GestureDetector
     * @param event
     * @return
     *//*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    *//**
     * drawerlayout滑动冲突，所以需要分发触摸事件
     * @param ev
     * @return
     *//*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        super.dispatchTouchEvent(ev);
        return false;
    }*/
}
