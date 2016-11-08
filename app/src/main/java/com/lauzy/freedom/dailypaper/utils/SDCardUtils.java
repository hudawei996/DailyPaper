package com.lauzy.freedom.dailypaper.utils;

import android.graphics.Bitmap;

import com.lauzy.freedom.dailypaper.app.MyApp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lauzy on 2016/11/7.
 */

public class SDCardUtils {

    /**
     * 保存图片到本地
     * @param bitmap
     * @param fileName
     */
    public static void saveImage(Bitmap bitmap, String fileName){
        BufferedOutputStream bos = null;
        try {
            File dir = new File(MyApp.SDCARD_DOWNLOAD_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(MyApp.SDCARD_DOWNLOAD_PATH + File.separator + fileName);
            bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
