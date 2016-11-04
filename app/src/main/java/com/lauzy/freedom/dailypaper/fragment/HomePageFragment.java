package com.lauzy.freedom.dailypaper.fragment;


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
import android.widget.ImageView;
import android.widget.TextView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.model.ZHhomePageBean;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.utils.Contants;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 采用commonadapter,处理不便，废弃此方法
 */

@Deprecated
public class HomePageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private CommonAdapter<ZHhomePageBean.StoriesBean> mHomePageAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private List<ZHhomePageBean.StoriesBean> mAllStories = new ArrayList<>();
    private String mDate;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;


    public HomePageFragment() {
    }


    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
//        RetrofitUtils.getZHhomePageBefore(mHomePageHandler, Contants.ZHHOME_PAGE_SUCCESS,"20161104");
        RetrofitUtils.getZHhomePageData(mHomePageHandler, Contants.ZHHOME_PAGE_SUCCESS);
    }

    private void initView(View view) {

        mRecyclerView = ((RecyclerView) view.findViewById(R.id.recyclerview_zh_homepage));
        mRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.srl_home_page));
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initRefresh();
    }

    private void initRefresh() {
        mRefreshLayout.setColorSchemeResources(R.color.color_selected_blue);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RetrofitUtils.getZHhomePageData(mHomePageHandler, Contants.ZHHOME_PAGE_SUCCESS);
            }
        });
    }

    private Handler mHomePageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Contants.ZHHOME_PAGE_SUCCESS:
                    ZHhomePageBean zHhomePageBean = (ZHhomePageBean) msg.obj;
                    List<ZHhomePageBean.StoriesBean> stories = zHhomePageBean.getStories();
                    mAllStories.addAll(stories);
                    String date = zHhomePageBean.getDate();
                    mDate = date;
                    mRefreshLayout.setRefreshing(false);
                    handleHomePageList(mAllStories, mDate);//首页数据
                    break;
               /* case Contants.ZHHOME_PAGE_BEFORE_SUCCESS:
                    ZHhomePageBean zHhomePageBeanBefore = (ZHhomePageBean) msg.obj;
                    String beforeDate = zHhomePageBeanBefore.getDate();
                    mDate = beforeDate;
                    List<ZHhomePageBean.StoriesBean> beforeStroies = zHhomePageBeanBefore.getStories();
                    mAllStories.addAll(beforeStroies);
                    break;*/
            }
        }
    };

    private void handleHomePageList(List<ZHhomePageBean.StoriesBean> stories, final String date) {

        mHomePageAdapter = new CommonAdapter<ZHhomePageBean.StoriesBean>(getContext(),
                R.layout.layout_zh_themelist_item, stories) {
            @Override
            protected void convert(ViewHolder holder, ZHhomePageBean.StoriesBean storiesBean, int position) {
                holder.setText(R.id.txt_zh_theme_content, storiesBean.getTitle());
                String imageUrl = storiesBean.getImages().get(0);
                ImageView imageView = (ImageView) holder.getConvertView().findViewById(R.id.img_zh_theme_content);
                Picasso.with(getContext())
                        .load(imageUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
            }
        };

        initHeader();

        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 1 == mHeaderAndFooterWrapper.getItemCount()) {
//                    RetrofitUtils.getZHhomePageBefore(mHomePageHandler, Contants.ZHHOME_PAGE_BEFORE_SUCCESS, date);
                    getBeforeData(date);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private void getBeforeData(String date) {

    }

    private void initHeader() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mHomePageAdapter);
        TextView textView = new TextView(getContext());
        textView.setText("test");
        mHeaderAndFooterWrapper.addHeaderView(textView);
    }
}
