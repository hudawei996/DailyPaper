package com.lauzy.freedom.dailypaper.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import solid.ren.skinlibrary.base.SkinBaseActivity;

@SuppressLint("SetJavaScriptEnabled")
public class ZHThemeItemActivity extends SkinBaseActivity implements View.OnClickListener {

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

        LinearLayout layout_tool = (LinearLayout) findViewById(R.id.layout_tool);
        dynamicAddView(layout_tool,"background",R.color.color_selected_blue);

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

                    String imageUrl = null;
                    String body = null;
                    String css = null;
                    if (zhThemeItemDetail != null) {
                        imageUrl = zhThemeItemDetail.getImage();
                        body = zhThemeItemDetail.getBody();
                        css = zhThemeItemDetail.getCss().get(0);
                        mTitle = zhThemeItemDetail.getTitle();
                        mImageUrl = zhThemeItemDetail.getImages() == null ? "error" : zhThemeItemDetail.getImages().get(0);
                    }
                    if (imageUrl != null) {
                        Picasso.with(MyApp.mContext)
                                .load(imageUrl)
                                .placeholder(R.mipmap.default_img)
                                .into(mImageView);
                    }
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

                        handleImage(sb.toString());

                    } else {
                        Toast.makeText(ZHThemeItemActivity.this, R.string.txt_getdata_failue, Toast.LENGTH_SHORT).show();
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

    @SuppressLint("JavascriptInterface")
    private void handleImage(String s) {
        Document document = Jsoup.parse(s);
        Elements img = document.select("img");
//        Log.e("TAG", "handleImage: " + img.toString());//<img class="avatar" src="http://pic1.zhimg.com/36b158fd8_is.jpg">
        mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        mWebView.setWebViewClient(new MyWebViewClient());
    }

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
                showShare();
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

    private void showShare() {
        ShareSDK.initSDK(this);

        String url = Contants.ZHIHU_BASE_URL+ Contants.ZHIHU_THEME_LIST_ITEM_DETAIL_URL + mItemID;

        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本" + url);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/21212.jpg");//确保SDcard下面存在此张图片

        oks.setImageUrl(mImageUrl);

// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);
    }


    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    public void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }


    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            Log.e("TAG", "openImage000: " + img);
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, ImageDetailActivity.class);
            context.startActivity(intent);
            Log.e("TAG", "openImage111: " + img);
        }
    }

}
