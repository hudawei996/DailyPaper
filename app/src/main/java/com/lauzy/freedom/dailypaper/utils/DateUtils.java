package com.lauzy.freedom.dailypaper.utils;

/**
 * Created by Lauzy on 2016/11/4.
 */
public class DateUtils {

    public static String formatDate(String date) {
        String dateFormat = null;
        try {
            dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

}
