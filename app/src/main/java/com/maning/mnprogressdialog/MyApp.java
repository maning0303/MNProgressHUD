package com.maning.mnprogressdialog;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author : maning
 * @desc :
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //内存泄露检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
