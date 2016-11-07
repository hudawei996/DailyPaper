package com.lauzy.freedom.dailypaper.service;

import com.lauzy.freedom.dailypaper.model.ZHNewsExtraBean;
import com.lauzy.freedom.dailypaper.model.ZHThemeItemDetail;
import com.lauzy.freedom.dailypaper.model.ZHThemeList;
import com.lauzy.freedom.dailypaper.model.ZHThemeListItem;
import com.lauzy.freedom.dailypaper.model.ZHhomePageBean;
import com.lauzy.freedom.dailypaper.utils.Contants;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lauzy on 2016/11/1.
 */

public interface ZhihuService {

    /**
     * 知乎主题日报列表
     * @return
     */
    @GET(Contants.ZHIHU_THEME_LIST_API)
    Observable<ZHThemeList> getZHThemeList();

    @GET(Contants.ZHIHU_THEME_LIST_ITEM_URL + "{itemID}")
    Observable<ZHThemeListItem> getZHThemeListItem(@Path("itemID") int itemID);

    @GET(Contants.ZHIHU_THEME_LIST_ITEM_DETAIL_URL + "{detailId}")
    Observable<ZHThemeItemDetail> getZHThemeItemDetail(@Path("detailId") int detailId);

    @GET(Contants.ZHIHU_NEWS_EXTRA_URL + "{newsId}")
    Observable<ZHNewsExtraBean> getZHNewsExtraData(@Path("newsId") int newsId);

    @GET(Contants.ZHIHU_HOME_PAGE_LATEST)
    Observable<ZHhomePageBean> getZHHomePageLatest();

   /* @GET(Contants.ZHIHU_HOME_PAGE_BEFORE + "{beforeDate}")
    Observable<ZHhomePageBefore> getZHHomePageBeforeList(@Path("beforeDate") String beforeDate);*/

    @GET(Contants.ZHIHU_HOME_PAGE_BEFORE + "{beforeDate}")
    Observable<ZHhomePageBean> getZHHomePageBeforeList(@Path("beforeDate") String beforeDate);
}
