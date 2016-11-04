package com.lauzy.freedom.dailypaper.service;

import com.lauzy.freedom.dailypaper.model.QiuBaiVideoBean;
import com.lauzy.freedom.dailypaper.utils.Contants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lauzy on 2016/11/4.
 */

public interface QiubaiService {
    @GET(Contants.QIUBAI_VIDEO_API)
    Observable<QiuBaiVideoBean> getQiubaiVideoData(@Query("page") int page);
}
