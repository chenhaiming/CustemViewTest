package com.chm.zf.UIutils;

import android.app.Application;

/**
 * Created by hyc on 2016/6/18.
 *
 */
public class App extends Application {
    private App _appComponent;

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }


    public App getAppComponent() {
        return _appComponent;
    }

    public static App getApp() {
        return app;
    }
}
