package com.lauzy.freedom.dailypaper.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.model.ZHThemeItemDetail;
import com.lauzy.freedom.dailypaper.net.RetrofitUtils;
import com.lauzy.freedom.dailypaper.utils.Contants;

public class ZHThemeItemActivity extends AppCompatActivity {

    public static final String ZHTHEME_ITEM_ID = "item_id";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhtheme_item);
        int itemID = getIntent().getIntExtra(ZHTHEME_ITEM_ID, -1);
        initView();
        initData(itemID);
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_zh_theme_item);
        toolbar.setNavigationIcon(R.mipmap.icon_back_white_64);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebView = (WebView) findViewById(R.id.webview_zh_theme_item);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ZHThemeItemActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler mZHThemeItemDetailHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Contants.ZHTHEME_LIST_ITEM_DETAIL_SUCCESS:
                    ZHThemeItemDetail zhThemeItemDetail = (ZHThemeItemDetail) msg.obj;
                    String body = zhThemeItemDetail.getBody();
                    String css = zhThemeItemDetail.getCss().get(0);

                    /*StringBuilder sb = new StringBuilder();
                    sb.append("<HTML><HEAD><LINK href=\"style.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
                    sb.append(body.toString());
                    sb.append("</body></HTML>");

                    mWebView.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "UTF-16", null);*/

                    if (body!=null){
                        StringBuilder sb = new StringBuilder();
                        sb.append("<HTML><HEAD><LINK href=\"" + css + "\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
                        sb.append(body.toString());
                        sb.append("</body></HTML>");

                        mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-16", null);
                    }

                    break;
            }
        }
    };

    private void initData(int itemID) {
        RetrofitUtils.getZHThemeItemDetail(mZHThemeItemDetailHandler, Contants.ZHTHEME_LIST_ITEM_DETAIL_SUCCESS, itemID);
    }
}
