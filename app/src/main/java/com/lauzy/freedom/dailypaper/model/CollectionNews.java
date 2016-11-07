package com.lauzy.freedom.dailypaper.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 收藏新闻实体类
 * Created by Lauzy on 2016/11/4.
 */

@Table(name = "news_collection",id = "_id")
public class CollectionNews extends Model{

    @Column(name = "urlId")
    private int urlId;

    @Column(name = "date")
    private String date;

    @Column(name = "title")
    private String title;

    @Column(name = "imgUrl")
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
