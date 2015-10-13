package com.xdsjs.save.application;

import android.app.Application;

/**
 * Created by xdsjs on 2015/10/13.
 */
public class MyApplication extends Application{

    private static MyApplication myApplication = null;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

}
