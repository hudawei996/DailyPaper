package com.lauzy.freedom.dailypaper.app;

import android.content.Context;
import android.os.Environment;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.lauzy.freedom.dailypaper.model.CollectionNews;

import java.io.File;

import solid.ren.skinlibrary.base.SkinBaseApplication;

/**
 * Created by Lauzy on 2016/11/1.
 */

public class MyApp extends SkinBaseApplication {

    public static Context mContext;

    //屏幕的高度和宽度的获取
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    //设置下载的SDCard的文件路径
    public static String SDCARD_DOWNLOAD_PATH;
    //图片文件夹的名字
    public static String DOWNLOAD_FOLDER = "daily_paper_images";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initActiveAndroid();
        initScreen();
        initDownLoadPath();

    }

    private void initDownLoadPath() {
        String dirPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            dirPath = mContext.getExternalFilesDir(null).getAbsolutePath();
        }else {
            dirPath = mContext.getFilesDir().getAbsolutePath();
        }
        SDCARD_DOWNLOAD_PATH = dirPath + File.separator + DOWNLOAD_FOLDER;//文件夹
    }

    private void initActiveAndroid() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(CollectionNews.class);
        ActiveAndroid.initialize(configurationBuilder.create());
//        ActiveAndroid.initialize(this);
    }

    public void initScreen() {
        SCREEN_WIDTH = mContext.getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = mContext.getResources().getDisplayMetrics().heightPixels;
    }
}
