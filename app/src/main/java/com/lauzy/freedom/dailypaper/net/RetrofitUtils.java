package com.lauzy.freedom.dailypaper.net;

import android.os.Handler;
import android.os.Message;

import com.lauzy.freedom.dailypaper.model.ZHThemeItemDetail;
import com.lauzy.freedom.dailypaper.model.ZHThemeList;
import com.lauzy.freedom.dailypaper.model.ZHThemeListItem;
import com.lauzy.freedom.dailypaper.service.ZhihuService;
import com.lauzy.freedom.dailypaper.utils.Contants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 封装retrofit获取网络数据
 * Created by Lauzy on 2016/11/1.
 */

public class RetrofitUtils {

    private Retrofit mZHThemeListRetrofit;

   /* public static RetrofitUtils instance;

    public RetrofitUtils getInstance(){
        if (instance == null){
            instance = new RetrofitUtils();
        }
        return instance;
    }*/

    /**
     * http://blog.csdn.net/janch1/article/details/50738565
     * 单例模式线程安全
     * @return
     */
    //在访问RetrofitUtils时创建单例
    private static class SingletonHolder{
        private static final RetrofitUtils instance = new RetrofitUtils();
    }
    //获取单例
    public static RetrofitUtils getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 知乎主题列表
     */
    private RetrofitUtils(){
        mZHThemeListRetrofit = new Retrofit.Builder()
                .baseUrl(Contants.ZHIHU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }
    public Retrofit getZHThemeListRetrofit(){
        return mZHThemeListRetrofit;
    }

    public static void getZHThemeListData(final Handler handler, final int what){
        ZhihuService zhihuService = getInstance().getZHThemeListRetrofit().create(ZhihuService.class);
        Observable<ZHThemeList> themeListObservable = zhihuService.getZHThemeList();
        themeListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZHThemeList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZHThemeList zhThemeList) {
                        Message message = handler.obtainMessage();
                        message.obj = zhThemeList;
                        message.what = what;
                        handler.sendMessage(message);
                    }
                });
    }

    public static void getZHThemeListItemData(final Handler handler, final int what,int urlID){
        ZhihuService zhihuService = getInstance().getZHThemeListRetrofit().create(ZhihuService.class);
        Observable<ZHThemeListItem> itemObservable = zhihuService.getZHThemeListItem(urlID);
        itemObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZHThemeListItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZHThemeListItem zhThemeList) {
                        Message message = handler.obtainMessage();
                        message.obj = zhThemeList;
                        message.what = what;
                        handler.sendMessage(message);
                    }
                });
    }

    public static void getZHThemeItemDetail(final Handler handler, final int what, int detailId){
        ZhihuService zhihuService = getInstance().getZHThemeListRetrofit().create(ZhihuService.class);
        Observable<ZHThemeItemDetail> detailObservable = zhihuService.getZHThemeItemDetail(detailId);
        detailObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZHThemeItemDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZHThemeItemDetail zhThemeItemDetail) {
                        Message message = handler.obtainMessage();
                        message.obj = zhThemeItemDetail;
                        message.what = what;
                        handler.sendMessage(message);
                    }
                });
    }

}
