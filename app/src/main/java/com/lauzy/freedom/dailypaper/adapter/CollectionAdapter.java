package com.lauzy.freedom.dailypaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.model.CollectionNews;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lauzy on 2016/11/6.
 */

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CollectionNews> mCollectionNewsList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CollectionAdapter(List<CollectionNews> collectionNewsList, Context context) {
        mCollectionNewsList = collectionNewsList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_zh_themelist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  ViewHolder){

            CollectionNews news = mCollectionNewsList.get(position);

            if (news.getImgUrl().equals("error")) {
                ((ViewHolder) holder).mDescTextView.setText(news.getTitle());
                ((ViewHolder) holder).mImageView.setVisibility(View.GONE);
            } else {
                ((ViewHolder) holder).mDescTextView.setText(news.getTitle());
                ((ViewHolder) holder).mImageView.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(news.getImgUrl())
                        .placeholder(R.mipmap.default_img)
                        .into(((ViewHolder) holder).mImageView);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCollectionNewsList == null ? 0 : mCollectionNewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mDescTextView;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img_zh_theme_content);
            mDescTextView = (TextView) itemView.findViewById(R.id.txt_zh_theme_content);
        }
    }
}
