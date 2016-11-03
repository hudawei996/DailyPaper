package com.lauzy.freedom.dailypaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.model.ZHThemeListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lauzy on 2016/11/1.
 */

public class HomeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TYPE_HEAD_TITLE = 0x0011;
    private int TYPE_CONTENT_ITEM = 0x0012;

    private Context mContext;
    private List<ZHThemeListItem.StoriesBean> mStoriesBeen;
    private List<ZHThemeListItem.EditorsBean> mEditorsBeen;
    private String mHeadImageUrl;
    private String mHeadDesc;
    private LayoutInflater mInflater;

    public HomeItemAdapter(Context context, List<ZHThemeListItem.StoriesBean> storiesBeen,
                           List<ZHThemeListItem.EditorsBean> editorsBeen, String headImageUrl, String desc) {
        mContext = context;
        mStoriesBeen = storiesBeen;
        mEditorsBeen = editorsBeen;
        mHeadImageUrl = headImageUrl;
        mHeadDesc = desc;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD_TITLE;
        } else {
            return TYPE_CONTENT_ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD_TITLE) {
            return new RvHeadHolder(mInflater.inflate(R.layout.layout_zh_themelist_head, parent, false));
        } else {
            return new RvViewHolder(mInflater.inflate(R.layout.layout_zh_themelist_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RvHeadHolder) {
            Picasso.with(mContext)
                    .load(mHeadImageUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(((RvHeadHolder) holder).mHeadImageView);

            //设置暗色，防止图片有白色文字无法显示
            ((RvHeadHolder) holder).mHeadImageView.setColorFilter(R.color.color_image_dark_alpha);

            ((RvHeadHolder) holder).mHeadTextView.setText(mHeadDesc);

            ((RvHeadHolder) holder).mEditorLayout.removeAllViews();//移除所有的子view，防止滑动时重复加载

            TextView textView = new TextView(mContext);
            textView.setText("编者");
            ((RvHeadHolder) holder).mEditorLayout.addView(textView);

            for (ZHThemeListItem.EditorsBean editorsBean : mEditorsBeen) {
                String avatarUrl = editorsBean.getAvatar();
                String editorUrl = editorsBean.getUrl();

                CircleImageView imageView = new CircleImageView(mContext);
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setPadding(10, 0, 10, 0);

                Picasso.with(mContext)
                        .load(avatarUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);

                ((RvHeadHolder) holder).mEditorLayout.addView(imageView);
            }

        } else {
            ZHThemeListItem.StoriesBean storiesBean = mStoriesBeen.get(position - 1);
            if (storiesBean.getImages() != null) {
                Picasso.with(mContext)
                        .load(storiesBean.getImages().get(0))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(((RvViewHolder) holder).mContenImageView);
                ((RvViewHolder) holder).mContenImageView.setVisibility(View.VISIBLE);
                ((RvViewHolder) holder).mContentTextView.setText(storiesBean.getTitle());
            } else {
                ((RvViewHolder) holder).mContenImageView.setVisibility(View.GONE);
                ((RvViewHolder) holder).mContentTextView.setText(storiesBean.getTitle());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStoriesBeen == null ? 0 : mStoriesBeen.size() + 1;
    }

    class RvHeadHolder extends RecyclerView.ViewHolder {

        public ImageView mHeadImageView;
        public TextView mHeadTextView;
        public LinearLayout mEditorLayout;

        public RvHeadHolder(View itemView) {
            super(itemView);
            mHeadImageView = (ImageView) itemView.findViewById(R.id.img_zh_theme_head);
            mHeadTextView = (TextView) itemView.findViewById(R.id.txt_zh_theme_head);
            mEditorLayout = (LinearLayout) itemView.findViewById(R.id.layout_zh_theme_head_editor);
        }
    }

    class RvViewHolder extends RecyclerView.ViewHolder {

        public TextView mContentTextView;
        public ImageView mContenImageView;

        public RvViewHolder(View itemView) {
            super(itemView);
            mContentTextView = (TextView) itemView.findViewById(R.id.txt_zh_theme_content);
            mContenImageView = (ImageView) itemView.findViewById(R.id.img_zh_theme_content);
        }
    }
}
