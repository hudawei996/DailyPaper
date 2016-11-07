package com.lauzy.freedom.dailypaper.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.activity.NewsCollectionActivity;
import com.squareup.picasso.Picasso;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class DrawerFragment extends Fragment implements View.OnClickListener {


    private Tencent mTencent;
    private UserInfo mInfo;
    private TextView mTextNickName;
    private TextView mTextGender;
    private ImageView mImageViewAvatar;
    private BaseListener mBaseListener;
    private boolean isLogin;
    private Button mButton;

    public DrawerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mTencent) {
            mTencent = Tencent.createInstance("222222", getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLoginView(view);

        initFunctionView(view);
    }

    private void initFunctionView(View view) {
        LinearLayout layoutCollection = (LinearLayout) view.findViewById(R.id.layout_news_collection);
        layoutCollection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layout_news_collection:
                startActivity(new Intent(getContext(), NewsCollectionActivity.class));
                break;
        }

    }

    private void initLoginView(View view) {
        mButton = (Button) view.findViewById(R.id.btn_login_exit);
        mTextNickName = (TextView) view.findViewById(R.id.txt_nick_name);
        mTextGender = (TextView) view.findViewById(R.id.txt_gender);
        mImageViewAvatar = (ImageView) view.findViewById(R.id.img_avatar);

        mBaseListener = new BaseListener() {
            @Override
            protected void doComplete(JSONObject response) {
//                            super.doComplete(response);
                initLoginData(response);
                getUserData();
            }
        };

        //                            super.doComplete(response);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTencent.isSessionValid()) {
                    mTencent.login((Activity) getContext(), "all", mBaseListener);
                    isLogin = true;
                } else {
                    if (!isLogin) { // SSO模式的登陆，先退出，再进行Server-Side模式登陆
                        mTencent.logout(getContext());
//                        mTencent.login((Activity) getContext(), "all", mBaseListener);
                        isLogin = true;
                        return;
                    }

                    if (mTencent != null && mTencent.isSessionValid()) {
                        if (isLogin) {
                            mButton.setText(R.string.txt_login);
                        } else {
                            mButton.setText(R.string.txt_exit);
                        }
                    } else {
                        mButton.setText(R.string.txt_login);
                    }

                    mTencent.logout(getContext());
                    getUserData();
                  /*  if (isLogin) {
                        mButton.setText(R.string.txt_login);
                    } else {
                        mButton.setText(R.string.txt_exit);
                    }*/
                }
            }
        });

    }

    private void initLoginData(JSONObject jsonObject) {
        try {
            if (jsonObject.getInt("ret") == 0) {
//                Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "initLoginData: ");
                String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                String openID = jsonObject.getString(Constants.PARAM_OPEN_ID);
                //**下面这两步设置很重要,如果没有设置,返回为空**
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(token, expires);
                getUserData();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 0X0111:
                    mButton.setText(R.string.txt_exit);
                    JSONObject object = (JSONObject) msg.obj;
                    try {
                        String imgUrl = object.getString("figureurl_2");
                        String name = object.getString("nickname");
                        String gender = object.getString("gender");
                        mTextGender.setText(gender);
                        mTextNickName.setText(name);

                        Picasso.with(getContext())
                                .load(imgUrl)
                                .placeholder(R.mipmap.default_img)
                                .into(mImageViewAvatar);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };


    private void getUserData() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }

                @Override
                public void onComplete(Object response) {
                    JSONObject jsonObject = (JSONObject) response;
                    Message message = mHandler.obtainMessage();
                    message.obj = jsonObject;
                    message.what = 0X0111;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(getContext(), mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }else {
            mTextGender.setText("");
            mTextNickName.setText("");
            mImageViewAvatar.setImageResource(R.mipmap.default_img);
        }
    }

    class BaseListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showAlert(getContext(), "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showAlert(getContext(), "返回为空", "登录失败");
                return;
            }

            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject response) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
}
