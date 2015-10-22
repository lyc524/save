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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdsjs on 2015/10/15.
 */
public abstract class BaseController {

    //监听获取后台预判记账类型的接口list
    private List<SyncListener> syncRemarkListener;

    public static interface SyncListener {
        void onSyncSuccess(List<BillType> billTypes);
    }

    protected Context context;

    protected BaseModel baseModel;

    private static BaseController baseController = null;

    protected BaseController() {
        baseController = this;
    }

    public synchronized boolean onInit(Context context) {
        this.context = context;
        baseModel = createModel();
        syncRemarkListener = new ArrayList<>();
        return true;
    }

    abstract protected BaseModel createModel();

    public static BaseController getInstance() {
        return baseController;
    }

    //后台获取预判的记账信息
    public void getForecastFormServer(Context context, final List<BillType> billTypes, final OnGetForecastFromServer onGetForecastFromServer) {
        RequestParams params = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("time", String.valueOf(TimeUtils.getCurrentTime()));
            for (BillType billType : billTypes)
                jsonObject.put(billType.getType(), String.valueOf(billType.getWeight()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json", jsonObject.toString());
        HttpUtils.post(Global.NETWORK_ACTION_GET_FORECAST_TYPE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        String type = json.getString("type");
                        BillType billType = new BillType();
                        for (int i = 0; i < billTypes.size(); i++) {
                            if (billTypes.get(i).getType().equals(type)) {
                                billType = billTypes.get(i);
                                billTypes.remove(i);
                                break;
                            }
                        }
                        billTypes.add(0, billType);
                        onGetForecastFromServer.onSuccess(billTypes);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onGetForecastFromServer.onFail();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onGetForecastFromServer.onFail();
            }
        });
    }

    public void addSyncRemarkListener(SyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncRemarkListener.contains(listener)) {
            syncRemarkListener.add(listener);
        }
    }

    public void removeSyncRemarkListener(SyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncRemarkListener.contains(listener)) {
            syncRemarkListener.remove(listener);
        }
    }

    public void noitifyRemarkSyncListeners(List<BillType> billTypes) {
        for (SyncListener listener : syncRemarkListener) {
            listener.onSyncSuccess(billTypes);
        }
    }


    public interface OnGetForecastFromServer {
        void onSuccess(List<BillType> billTypes);

        void onFail();
    }
}
