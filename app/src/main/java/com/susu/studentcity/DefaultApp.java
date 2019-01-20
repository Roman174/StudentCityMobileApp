package com.susu.studentcity;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class DefaultApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
