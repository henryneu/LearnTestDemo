package com.bkjk.infra.test;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/17
 * Version: 1.0.0
 * Description:
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
