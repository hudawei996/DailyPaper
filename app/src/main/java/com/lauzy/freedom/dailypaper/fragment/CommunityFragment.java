package com.lauzy.freedom.dailypaper.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lauzy.freedom.dailypaper.R;

public class CommunityFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ImageView mImageView;
    private int mWidth;
    private int mHeight;
    private int mRotation;
    private boolean isMove;
    private ObjectAnimator mAnimator;


    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.img_circle);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (!isMove) {


            PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("TranslationX", 0, -150, 0, 150, 0, -150, 0, 150, 0);
            PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("TranslationY", 0, -200, 0, 200, 0, 200, 0, -200, 0);
            PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("RotationY", 0.0f, 720.0f);

            mAnimator = ObjectAnimator.ofPropertyValuesHolder(mImageView, holderX, holderY, holderR);
            mAnimator.setDuration(3000);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.start();

            isMove = true;
            /*ObjectAnimator animatorX = ObjectAnimator.ofFloat(v, "TranslationX", 0, -100, 0, 100, 0);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(v, "TranslationY", 0, -50, 0, 50, 0);
            ObjectAnimator animatorRotaion = ObjectAnimator.ofFloat(v, "RotationX", 0.0f, 360.0f);*/

        } else {
            isMove = false;
            mAnimator.cancel();
            Log.e("TAG", "onClick: false");
        }
    }
}
