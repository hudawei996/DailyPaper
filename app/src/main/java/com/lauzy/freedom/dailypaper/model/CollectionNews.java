package com.lauzy.freedom.dailypaper.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Lauzy on 2016/11/4.
 */

@Table(name = "news_collection",id = "_id")
public class CollectionNews extends Model{

    @Column(name = "urlId")
    private String urlId;

    @Column(name = "date")
    private String date;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
