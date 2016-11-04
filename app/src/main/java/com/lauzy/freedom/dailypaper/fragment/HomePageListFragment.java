package com.lauzy.freedom.dailypaper.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.adapter.HomeListAdapter;
import com.lauzy.freedom.dailypaper.app.MyApp;
import com.lauzy.freedom.dailypaper.model.ZHhomePageBean;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.service.ZhihuService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomePageListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mCurrentDate;
    private ListView mListView;
    private List<ZHhomePageBean.StoriesBean> mStoriesBeen = new ArrayList<>();
//    private ZHhomePageBean mZHhomePageBean;
    private HomeListAdapter mAdapter;
    private boolean isLastItem;
    private SwipeRefreshLayout mRefreshLayout;


    public HomePageListFragment() {
    }


    public static HomePageListFragment newInstance(String param1, String curDate) {
        HomePageListFragment fragment = new HomePageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, curDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mCurrentDate = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.srl_home_list));
        mListView = ((ListView) view.findViewById(R.id.lv_home_list));
        mAdapter = new HomeListAdapter(getActivity(),mStoriesBeen);
        mListView.setAdapter(mAdapter);
        getLatestData();

        initRefresh();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == SCROLL_STATE_IDLE) {
                    loadBeforeData(mCurrentDate);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isLastItem = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });

    }

    private void initRefresh() {
        mRefreshLayout.setColorSchemeResources(R.color.color_selected_blue);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearAllData();//先移除所有数据，否则刷新则增加最新数据
                getLatestData();
            }
        });
    }

    private void loadBeforeData(String currentDate) {
        ZhihuService zhihuService = RetrofitUtils.getInstance().getZHRetrofit().create(ZhihuService.class);
        Observable<ZHhomePageBean> pageBeanObservable = zhihuService.getZHHomePageBeforeList(currentDate);
        pageBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZHhomePageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), R.string.txt_getdata_failue, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ZHhomePageBean zHhomePageBean) {
                        List<ZHhomePageBean.StoriesBean> stories = zHhomePageBean.getStories();
                        mCurrentDate = zHhomePageBean.getDate();
                        for (ZHhomePageBean.StoriesBean story : stories) {
                            story.setDate(zHhomePageBean.getDate());
                        }
                        mAdapter.addBeforeNewsData(stories);
                    }
                });
    }

    private void getLatestData() {
        ZhihuService zhihuService = RetrofitUtils.getInstance().getZHRetrofit().create(ZhihuService.class);
        Observable<ZHhomePageBean> pageBeanObservable = zhihuService.getZHHomePageLatest();
        pageBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZHhomePageBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: ");
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), R.string.txt_getdata_failue, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(final ZHhomePageBean zHhomePageBean) {

                        mRefreshLayout.setRefreshing(false);
                        List<ZHhomePageBean.StoriesBean> stories = zHhomePageBean.getStories();
                        mCurrentDate = zHhomePageBean.getDate();
                        for (ZHhomePageBean.StoriesBean story : stories) {
                            story.setDate(zHhomePageBean.getDate());
                        }
                       /* mListView.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new HomeListAdapter(getContext(),zHhomePageBean);
                                mListView.setAdapter(mAdapter);
                            }
                        });*/
                        mAdapter.addNewsData(stories);
//                        mAdapter.addLatestBean(zHhomePageBean);
                    }
                });
    }
}
