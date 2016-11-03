package com.lauzy.freedom.dailypaper.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lauzy on 2016/11/1.
 */

public class MyApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
