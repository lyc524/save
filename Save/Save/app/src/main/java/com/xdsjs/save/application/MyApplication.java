package com.xdsjs.save.application;

import android.app.Application;

import com.xdsjs.save.controller.MyController;
import com.xdsjs.save.utils.LogUtils;

/**
 * 在这里完成app的初始化
 * Created by xdsjs on 2015/10/13.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    private MyController myController = new MyController();

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //设置是否打印日志,默认即为true
        LogUtils.isDebug = true;
        myController.onInit(this);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

}
