package com.xdsjs.save.controller;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.model.BaseModel;
import com.xdsjs.save.network.HttpUtils;
import com.xdsjs.save.utils.TimeUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by xdsjs on 2015/10/15.
 */
public abstract class BaseController {

    protected Context context;

    protected BaseModel baseModel;

    private static BaseController baseController = null;

    protected BaseController() {
        baseController = this;
    }

    public synchronized boolean onInit(Context context) {
        this.context = context;
        baseModel = createModel();
        return true;
    }

    abstract protected BaseModel createModel();

    public static BaseController getInstance() {
        return baseController;
    }

    //后台获取预判的记账信息
    public void getForecastFormServer(Context context, List<BillType> billTypes) {
        RequestParams params = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("time", String.valueOf(TimeUtils.getCurrentTime()));
            for (BillType billType : billTypes)
                jsonObject.put(billType.getType(), String.valueOf(billType.getWeight()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json", jsonObject);
        HttpUtils.post(Global.NETWORK_ACTION_GET_FORECAST_TYPE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
