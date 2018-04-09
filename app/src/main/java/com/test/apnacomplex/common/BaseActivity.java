package com.test.apnacomplex.common;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.test.apnacomplex.R;

/**
 * Created by sc on 3/4/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private boolean mShouldAnimateActivity = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performActivityEntryAnimation();
    }

    /**
     * Set to true for activity if animation for entry/exit required
     *
     * @param shouldAnimate
     */
    protected void setShouldAnimateActivity(boolean shouldAnimate) {
        mShouldAnimateActivity = shouldAnimate;
    }

    private void performActivityExitAnimation() {
        if (mShouldAnimateActivity) {
            overridePendingTransition(R.anim.activity_anim_stay, R.anim.slide_right_from_right);
        }
    }

    private void performActivityEntryAnimation() {
        if (mShouldAnimateActivity) {
            overridePendingTransition(R.anim.slide_left_from_right, R.anim.activity_anim_stay);
        }
    }

    @Override
    public void finish() {
        super.finish();
        performActivityExitAnimation();
    }
}