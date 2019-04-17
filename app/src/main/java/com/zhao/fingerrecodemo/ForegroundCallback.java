package com.zhao.fingerrecodemo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.math.BigDecimal;
import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ForegroundCallback implements Application.ActivityLifecycleCallbacks {
    public static final long CHECK_DELAY = 500;
    public static final String TAG = ForegroundCallback.class.getName();
    private long time = -1;
    private int count = 0;
    private Application application;

    public void init(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i(TAG, "onActivityStarted()");
        if (count == 0) {
            if (time != -1) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                Date date2 = new Date();
                long returnTime = date2.getTime();
                double sub = new BigDecimal(returnTime).subtract(new BigDecimal(time)).doubleValue();
                if (sub >= 5000d) {//此处是判断应用到后台多久时间以后需要开启手势密码
                    jumpGuestActivity();
                }
            }
        }
        count++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
        if (count == 0) {
            Log.i(TAG, "切到后台  lifecycle");
            Date date = new Date();
            time = date.getTime();
        } else {
            time = -1;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    private void jumpGuestActivity() {
        Intent intent = new Intent(application, FingerPrintActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }
}

