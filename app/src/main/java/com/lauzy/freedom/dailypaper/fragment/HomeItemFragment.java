package com.lauzy.freedom.dailypaper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.activity.ZHThemeItemActivity;
import com.lauzy.freedom.dailypaper.adapter.HomeItemAdapter;
import com.lauzy.freedom.dailypaper.app.MyApp;
import com.lauzy.freedom.dailypaper.model.ZHThemeListItem;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.utils.Contants;
import com.lauzy.freedom.dailypaper.utils.RvItemClickListener;
import com.lauzy.freedom.dailypaper.utils.RvItemTouchListener;

import java.util.List;


public class HomeItemFragment extends Fragment {

    private static final String HOME_ITEM_NAME = "home_item_name";
    private static final String HOME_ITEM_ID = "home_item_id";

    private String mParam1;
    private int mParam2;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private HomeItemAdapter mAdapter;


    public HomeItemFragment() {
    }

    public static HomeItemFragment newInstance(String param1, int param2) {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        args.putString(HOME_ITEM_NAME, param1);
        args.putInt(HOME_ITEM_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(HOME_ITEM_NAME);
            mParam2 = getArguments().getInt(HOME_ITEM_ID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_item, container, false);
    }

    private Handler mZHThemeListItemHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Contants.ZHTHEME_LIST_ITEM_SUCCESS:
                    ZHThemeListItem zhThemeListItem = (ZHThemeListItem) msg.obj;
                    handleData(zhThemeListItem);
                    break;
                case Contants.ZH_GETDATA_FAILURE:
                    mRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), R.string.txt_getdata_failue, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void handleData(ZHThemeListItem zhThemeListItem) {
        final List<ZHThemeListItem.StoriesBean> stories = zhThemeListItem.getStories();
        List<ZHThemeListItem.EditorsBean> editors = zhThemeListItem.getEditors();
        String zhThemeListItemImage = zhThemeListItem.getImage();
        String description = zhThemeListItem.getDescription();
        mAdapter = new HomeItemAdapter(getContext(),
                stories, editors, zhThemeListItemImage, description);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setRefreshing(false);

        //自定义添加点击事件
        mRecyclerView.addOnItemTouchListener(new RvItemTouchListener(getContext(), mRecyclerView, new RvItemClickListener() {
            @Override
            public void rvItemClick(int positon) {
                if (positon >= 1 && positon <= stories.size()) {
                    int itemID = stories.get(positon - 1).getId();
                    stories.get(positon - 1).setRead(true);//设置为已读
                    mAdapter.notifyDataSetChanged();
                    //需要设置ZHThemeItemActivity 为singletask启动模式，否则下拉刷新，栈内可能会出现多个activity
                    Intent intent = new Intent(getActivity(), ZHThemeItemActivity.class);
                    intent.putExtra(ZHThemeItemActivity.ZHTHEME_ITEM_ID, itemID);
                    startActivity(intent);
                }
            }

            @Override
            public void rvItemLongClick(int positon) {
            }
        }));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.srl_home_item));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_home_item);
        initRefresh();
        RetrofitUtils.getZHThemeListItemData(mZHThemeListItemHandler, Contants.ZHTHEME_LIST_ITEM_SUCCESS, mParam2);
    }

    private void initRefresh() {
        mRefreshLayout.setColorSchemeResources(R.color.color_selected_blue);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mAdapter.getItemCount() != 0) {
                    mAdapter.clearAllData();
                }
                RetrofitUtils.getZHThemeListItemData(mZHThemeListItemHandler, Contants.ZHTHEME_LIST_ITEM_SUCCESS, mParam2);
            }
        });
    }
}
