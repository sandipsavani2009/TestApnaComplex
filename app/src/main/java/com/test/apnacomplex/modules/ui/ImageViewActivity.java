package com.test.apnacomplex.modules.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.test.apnacomplex.R;
import com.test.apnacomplex.common.BaseActivity;
import com.test.apnacomplex.utils.Constants;
import com.test.apnacomplex.utils.FileUtil;

import java.io.File;

public class ImageViewActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setShouldAnimateActivity(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_view);
        setupToolbar();
        initViews();
        showImage();
    }

    private void initViews() {
        findViewById(R.id.back_icon).setOnClickListener(this);
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.imgViewer_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void showImage() {
        if (getIntent() != null) {
            String fileName = getIntent().getStringExtra(Constants.BundleKeys.IMAGE_NAME);

            if (!TextUtils.isEmpty(fileName)) {
                File file = new File(FileUtil.getDirPath(), fileName);
                if (file.exists()) {

                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    if (bitmap != null) {

                        ImageView imageView = (ImageView) findViewById(R.id.imageViewer);
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
        }
    }
}
