package com.lauzy.freedom.dailypaper.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.app.MyApp;
import com.lauzy.freedom.dailypaper.model.ZHNewsExtraBean;
import com.lauzy.freedom.dailypaper.model.ZHThemeItemDetail;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.utils.Contants;
import com.squareup.picasso.Picasso;

public class ZHThemeItemActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ZHTHEME_ITEM_ID = "item_id";
    private WebView mWebView;
    private ImageView mImageView;
    private TextView mTextComment;
    private TextView mTextSupport;
    private int mItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhtheme_item);
        mItemID = getIntent().getIntExtra(ZHTHEME_ITEM_ID, -1);
        initView();
        initData(mItemID);
    }

    private void initView() {

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


                break;
            case R.id.imgbtn_comment:
                break;
            case R.id.imgbtn_support:
                break;
        }
    }
}
