package com.xdsjs.save.controller;

import android.content.Context;

import com.xdsjs.save.model.BaseModel;
import com.xdsjs.save.model.MyModel;

/**
 * Created by xdsjs on 2015/10/15.
 */
public class MyController extends BaseController {

    public synchronized boolean onInit(Context context) {
        if (super.onInit(context)) {
            //一些初始化操作
            return true;
        }

        return false;
    }

    @Override
    protected BaseModel createModel() {
        return new MyModel(context);
    }

    public MyModel getMyModel() {
        return (MyModel) baseModel;
    }


    public void logOut() {
        getMyModel().closeDB();
        getMyModel().logOut();
    }
}
