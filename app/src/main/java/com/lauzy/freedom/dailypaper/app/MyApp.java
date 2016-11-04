package com.lauzy.freedom.dailypaper.app;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.lauzy.freedom.dailypaper.model.CollectionNews;

/**
 * Created by Lauzy on 2016/11/1.
 */

public class MyApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initActiveAndroid();
    }

    private void initActiveAndroid() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(CollectionNews.class);
        ActiveAndroid.initialize(configurationBuilder.create());

//        ActiveAndroid.initialize(this);
    }
}
