package com.wecanstudio.xdsjs.save;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;

import com.wecanstudio.xdsjs.save.Model.db.DBManager;

/**
 * Created by xdsjs on 2015/11/17.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private Activity mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getContext() {
        return instance;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(@NonNull Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
}
