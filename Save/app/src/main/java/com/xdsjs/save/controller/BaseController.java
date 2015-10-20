package com.xdsjs.save.controller;

import android.content.Context;

import com.xdsjs.save.model.BaseModel;

/**
 * Created by xdsjs on 2015/10/15.
 */
public abstract class BaseController {

    protected Context context;

    protected BaseModel baseModel;

    private static BaseController baseController = null;

    protected BaseController(){
        baseController = this;
    }

    public synchronized boolean onInit(Context context) {
        this.context = context;
        baseModel = createModel();
        return true;
    }

    abstract protected BaseModel createModel();

    public static BaseController getInstance(){
        return baseController;
    }
}
