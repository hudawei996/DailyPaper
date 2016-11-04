package com.lauzy.freedom.dailypaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.activity.ZHThemeItemActivity;
import com.lauzy.freedom.dailypaper.model.ZHhomePageBean;
import com.lauzy.freedom.dailypaper.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lauzy on 2016/11/3.
 */

public class HomeListAdapter extends BaseAdapter {

    private List<ZHhomePageBean.StoriesBean> mStoriesBeen;
    private Context mContext;
    private LayoutInflater mInflater;
    private ZHhomePageBean mZHhomePageBean;


    public HomeListAdapter(Context context, List<ZHhomePageBean.StoriesBean> storiesBeen) {
        mContext = context;

       /* mZHhomePageBean = zHhomePageBean;

        if (zHhomePageBean != null) {
            mStoriesBeen = zHhomePageBean.getStories();
        }*/
        mStoriesBeen = storiesBeen;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mStoriesBeen == null ? 0 : mStoriesBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mStoriesBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_zh_home_list_item, parent, false);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.img_zh_homelist_content);
            holder.mDescTextView = (TextView) convertView.findViewById(R.id.txt_zh_homelist_content);
            holder.mDateTextView = (TextView) convertView.findViewById(R.id.txt_homelist_date);
            holder.mCardView = (CardView) convertView.findViewById(R.id.cardview_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position <= getCount()) {
            ZHhomePageBean.StoriesBean storiesBean = mStoriesBeen.get(position);
            Picasso.with(mContext)
                    .load(storiesBean.getImages().get(0))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.mImageView);

            if (storiesBean.isRead()) {
                holder.mDescTextView.setTextColor(Color.GRAY);
            } else {
                holder.mDescTextView.setTextColor(Color.BLACK);
            }

            holder.mDescTextView.setText(storiesBean.getTitle());

            if (position == 0) {
                holder.mDateTextView.setVisibility(View.VISIBLE);
                holder.mDateTextView.setText(R.string.txt_home_news_title);
            } else {
                ZHhomePageBean.StoriesBean newStory = mStoriesBeen.get(position - 1);
                if (storiesBean.getDate().equals(newStory.getDate())) {
                    holder.mDateTextView.setVisibility(View.GONE);
                }else {
                    holder.mDateTextView.setVisibility(View.VISIBLE);
                    holder.mDateTextView.setText(DateUtils.formatDate(storiesBean.getDate()));
                }
            }

            holder.mCardView.setOnClickListener(getListener(holder, storiesBean));
        }

        return convertView;
    }


    private View.OnClickListener getListener(final ViewHolder holder, final ZHhomePageBean.StoriesBean storiesBean) {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storiesBean.setRead(true);
                holder.mDescTextView.setTextColor(Color.GRAY);

                int storiesBeanId = storiesBean.getId();
                //需要设置ZHThemeItemActivity 为singletask启动模式，否则下拉刷新，栈内可能会出现多个activity
                Intent intent = new Intent(mContext, ZHThemeItemActivity.class);
                intent.putExtra(ZHThemeItemActivity.ZHTHEME_ITEM_ID, storiesBeanId);
                mContext.startActivity(intent);
            }
        };
        return listener;
    }

    public void addNewsData(List<ZHhomePageBean.StoriesBean> stories) {
        mStoriesBeen.addAll(stories);
        notifyDataSetChanged();
    }

    public void clearAllData() {
        mStoriesBeen.clear();
        notifyDataSetChanged();
    }

    public void addBeforeNewsData(List<ZHhomePageBean.StoriesBean> stories) {
        if (stories == null) {
            addNewsData(stories);
        } else {
            mStoriesBeen.addAll(stories);
            notifyDataSetChanged();
        }
    }

    /*public void addLatestBean(ZHhomePageBean zHhomePageBean) {
        mZHhomePageBean = zHhomePageBean;
        notifyDataSetChanged();
    }*/

    class ViewHolder {
        public TextView mDateTextView;
        public ImageView mImageView;
        public TextView mDescTextView;
        public CardView mCardView;
    }
}
