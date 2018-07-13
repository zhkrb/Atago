package com.zhkrb.atago;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;

import com.tencent.bugly.crashreport.CrashReport;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
    }
}
