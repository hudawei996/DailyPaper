package com.lauzy.freedom.dailypaper.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lauzy.freedom.dailypaper.R;
import com.lauzy.freedom.dailypaper.app.MyApp;
import com.lauzy.freedom.dailypaper.utils.SDCardUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ImageDetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private String mImgUrl;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        initToolbar();
        mImageView = (ImageView) findViewById(R.id.img_detail);
        mImgUrl = getIntent().getStringExtra("image");
        setImageView();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tootbar_img_detail);
        toolbar.setNavigationIcon(R.mipmap.icon_back_white_64);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDetailActivity.this.finish();
            }
        });

        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (Build.VERSION.SDK_INT >= 23) {
                    ImageDetailActivityPermissionsDispatcher.writeExternalStorageWithCheck(ImageDetailActivity.this);
                }else {
                    saveThePicture();
                }
                break;
        }
        return false;
    }

    private void saveThePicture() {
        final String fileName = mImgUrl.substring(mImgUrl.lastIndexOf("/") + 1);
        Picasso.with(this).load(mImgUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (bitmap != null) {
                    SDCardUtils.saveImage(bitmap, fileName);
                    Toast.makeText(ImageDetailActivity.this, R.string.txt_save_success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(ImageDetailActivity.this, R.string.txt_save_failure, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImageDetailActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void writeExternalStorage() {
        saveThePicture();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForExternalStorage(PermissionRequest request) {
        showRationaleDialog(R.string.txt_need_permission, request);
    }

    private void showRationaleDialog(int stringPoint, PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.txt_allow_permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);

                       /* Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                        }
                        startActivity(localIntent);*/

                    }
                })
                .setNegativeButton(R.string.txt_deny_permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ImageDetailActivity.this, R.string.txt_deny_permission, Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .setMessage(stringPoint)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onExternalStorageDenied() {
        Toast.makeText(this, R.string.txt_deny_permission_clew, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onExternalStorageNeverAskAgain() {
        Toast.makeText(this, R.string.txt_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_img_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //需要获取bitmap来重新设定宽高，采用Picasso的Target
        /*int imgWidth = MyApp.SCREEN_WIDTH;
        if (mImageView.getMeasuredWidth() != 0) {
            int imgHeight = (int) (mImageView.getMeasuredHeight() *1.0f/ (mImageView.getMeasuredWidth())* imgWidth);
            mImageView.setLayoutParams(new LinearLayout.LayoutParams(imgWidth,imgHeight));
            Log.e("TAG", "onWindowFocusChanged: " + imgWidth + "height is:" + imgHeight);
        }*/
    }

    private void setImageView() {
        Picasso.with(this).load(mImgUrl).placeholder(R.mipmap.default_img).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();
                int imgWidth = MyApp.SCREEN_WIDTH;
                int imgHeight = (int) (imgWidth * (bitmapHeight * 1.0f / bitmapWidth));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imgWidth, imgHeight);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mImageView.setLayoutParams(layoutParams);
                mImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mImageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                mImageView.setImageResource(R.mipmap.default_img);
            }
        });
    }
}
