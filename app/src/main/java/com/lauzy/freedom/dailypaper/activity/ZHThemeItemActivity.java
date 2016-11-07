package com.lauzy.freedom.dailypaper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.app.MyApp;
import com.lauzy.freedom.dailypaper.model.CollectionNews;
import com.lauzy.freedom.dailypaper.model.ZHNewsExtraBean;
import com.lauzy.freedom.dailypaper.model.ZHThemeItemDetail;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.utils.Contants;
import com.lauzy.freedom.dailypaper.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ZHThemeItemActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ZHTHEME_ITEM_ID = "item_id";
    private WebView mWebView;
    private ImageView mImageView;
    private TextView mTextComment;
    private TextView mTextSupport;
    private int mItemID;
    private LinearLayout mView;
    private String mTitle;
    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhtheme_item);

        mItemID = getIntent().getIntExtra(ZHTHEME_ITEM_ID, -1);
        initView();
        initData(mItemID);
    }

    private void initView() {

        mView = (LinearLayout) findViewById(R.id.layout_zh_news);

        mImageView = (ImageView) findViewById(R.id.img_zh_list_item);
        ImageButton ibBack = (ImageButton) findViewById(R.id.imgbtn_back);
        ImageButton ibShare = (ImageButton) findViewById(R.id.imgbtn_share);
        ImageButton ibCollection = (ImageButton) findViewById(R.id.imgbtn_collection);
        ImageButton ibComment = (ImageButton) findViewById(R.id.imgbtn_comment);
        ImageButton ibSupport = (ImageButton) findViewById(R.id.imgbtn_support);

        mTextComment = (TextView) findViewById(R.id.txt_comment_num);
        mTextSupport = (TextView) findViewById(R.id.txt_support_num);

        mWebView = (WebView) findViewById(R.id.webview_zh_theme_item);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());

        ibBack.setOnClickListener(this);
        ibShare.setOnClickListener(this);
        ibCollection.setOnClickListener(this);
        ibComment.setOnClickListener(this);
        ibSupport.setOnClickListener(this);
    }

    private Handler mZHThemeItemDetailHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Contants.ZHTHEME_LIST_ITEM_DETAIL_SUCCESS:
                    ZHThemeItemDetail zhThemeItemDetail = (ZHThemeItemDetail) msg.obj;
                    String imageUrl = zhThemeItemDetail.getImage();
                    if (imageUrl != null) {
                        Picasso.with(MyApp.mContext)
                                .load(imageUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(mImageView);
                    }
                    String body = zhThemeItemDetail.getBody();
                    String css = zhThemeItemDetail.getCss().get(0);
                    mTitle = zhThemeItemDetail.getTitle();
                    mImageUrl = zhThemeItemDetail.getImages() == null ? "error" : zhThemeItemDetail.getImages().get(0);
                    /*StringBuilder sb = new StringBuilder();
                    sb.append("<HTML><HEAD><LINK href=\"style.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
                    sb.append(body.toString());
                    sb.append("</body></HTML>");
                    mWebView.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "UTF-16", null);*/

                    if (body != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("<HTML><HEAD><LINK href=\"" + css + "\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
                        sb.append(body.toString());
                        sb.append("</body></HTML>");
                        mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
                        sb.toString();
                    }
                    break;

                case Contants.ZHNEWS_EXTRA_SUCCESS:
                    ZHNewsExtraBean zhNewsExtraBean = (ZHNewsExtraBean) msg.obj;
                    int comments = zhNewsExtraBean.getComments();
                    int popularity = zhNewsExtraBean.getPopularity();

                    mTextComment.setText(comments + "");
                    mTextSupport.setText(popularity + "");

                    break;
            }
        }
    };

    private void initData(int itemID) {
        RetrofitUtils.getZHThemeItemDetail(mZHThemeItemDetailHandler,
                Contants.ZHTHEME_LIST_ITEM_DETAIL_SUCCESS, itemID);
        RetrofitUtils.getZHNewsExtraData(mZHThemeItemDetailHandler,
                Contants.ZHNEWS_EXTRA_SUCCESS, itemID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_back:
                ZHThemeItemActivity.this.finish();
                break;
            case R.id.imgbtn_share:
                break;
            case R.id.imgbtn_collection:

                List<CollectionNews> collectionNewsList = new Select().from(CollectionNews.class).execute();

                String currentDate = DateUtils.getCurrentDate();
                CollectionNews news = new CollectionNews();
                news.setDate(currentDate);
                news.setUrlId(mItemID);
                news.setImgUrl(mImageUrl);
                news.setTitle(mTitle);

                if (collectionNewsList.size() == 0) {
                    news.save();
                    Snackbar.make(mView, R.string.txt_collection_success, Snackbar.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < collectionNewsList.size(); i++) {
                        if (collectionNewsList.get(i).getUrlId() == mItemID) {
                            Snackbar.make(mView, R.string.txt_collection_already_done, Snackbar.LENGTH_SHORT).show();
                            news = null;
                            break;
                        } else {
                            Snackbar.make(mView, R.string.txt_collection_success, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    if (news != null) {
                        news.save();
                    }
                }
                break;
            case R.id.imgbtn_comment:
//                new Delete().from(CollectionNews.class).where("urlId like ?", "8______").execute();//清空测试
                break;
            case R.id.imgbtn_support:
                break;
        }
    }
}
