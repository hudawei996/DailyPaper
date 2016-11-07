package com.lauzy.freedom.dailypaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.adapter.CollectionAdapter;
import com.lauzy.freedom.dailypaper.model.CollectionNews;
import com.lauzy.freedom.dailypaper.utils.RvItemClickListener;
import com.lauzy.freedom.dailypaper.utils.RvItemTouchListener;

import java.util.List;

public class NewsCollectionActivity extends AppCompatActivity {

    private Toolbar mToolbarBack;
    private RecyclerView mRecyclerView;
    private List<CollectionNews> mNewsList;
    private CollectionAdapter mAdapter;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_collection);
        initData();
        initView();
    }

    private void initData() {
        mNewsList = new Select().from(CollectionNews.class).execute();
    }

    private void initView() {
        mToolbarBack = (Toolbar) findViewById(R.id.toolbar_collection);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_collection);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mToolbarBack.setNavigationIcon(R.mipmap.icon_back_white_64);
        mToolbarBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsCollectionActivity.this.finish();
            }
        });

        if (mNewsList.isEmpty()) {
            Toast.makeText(this, R.string.txt_no_collection, Toast.LENGTH_SHORT).show();
        } else {
            mAdapter = new CollectionAdapter(mNewsList, this);
            mRecyclerView.setAdapter(mAdapter);
        }

        mRecyclerView.addOnItemTouchListener(new RvItemTouchListener(this, mRecyclerView, new RvItemClickListener() {
            @Override
            public void rvItemClick(int positon) {
                CollectionNews news = mNewsList.get(positon);
                int urlId = news.getUrlId();
                Log.e("TAG", "rvItemClick: " + urlId);
                Intent intent = new Intent(NewsCollectionActivity.this, ZHThemeItemActivity.class);
                intent.putExtra(ZHThemeItemActivity.ZHTHEME_ITEM_ID, urlId);
                startActivity(intent);
            }

            @Override
            public void rvItemLongClick(int positon) {
                Log.e("TAG", "rvItemLongClick: " + positon);
                mCurrentPosition = positon;
                PopupMenu popupMenu = new PopupMenu(NewsCollectionActivity.this, mRecyclerView.getChildAt(positon));
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        removeItem(mCurrentPosition);
                        return false;
                    }
                });
                popupMenu.show();

            }
        }));

    }

    private void removeItem(int currentPosition) {
        Log.e("TAG", "onMenuItemClick: " + mNewsList.get(currentPosition).getUrlId());
        new Delete().from(CollectionNews.class).where("urlId = ?", mNewsList.get(currentPosition).getUrlId()).execute();
        mNewsList.remove(currentPosition);
        mAdapter.notifyItemRemoved(currentPosition);
    }
}
