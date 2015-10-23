package com.xdsjs.save.controller;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.model.BaseModel;
import com.xdsjs.save.network.HttpUtils;
import com.xdsjs.save.utils.TimeUtils;

import org.apache.http.Header;
import org.json.JSONArray;
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
                jsonObject.put(billType.getType(), String.valueOf(billType.getTime()));
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
                        Log.e("后台获取预判的记账信息", json.toString());
                        if (json.getString("result").equals("1")) {
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
                            for (int i = 0; i < billTypes.size(); i++) {
                                if (billTypes.get(i).getType().equals("type_0")) {
                                    billType = billTypes.get(i);
                                    billTypes.remove(i);
                                    break;
                                }
                            }
                            billTypes.add(0, billType);
                            onGetForecastFromServer.onSuccess(billTypes);
                        }
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

    //后台获取账单信息
    public void getBillListFormServer(final OnGetAllBillListFromServer onGetAllBillListFromServer) {
        RequestParams params = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", ((MyController) BaseController.getInstance()).getMyModel().getPersonalAccount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json", jsonObject.toString());
        HttpUtils.post(Global.NETWORK_ACTION_GET_USER_BILL_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.e("后台获取账单信息", json.toString());
                        if (json.getString("result").equals("1")) {
                            JSONArray jsonArray = json.getJSONArray("bills");
                            if (!(jsonArray.length() == 0)) {
                                List<Bill> bills = new ArrayList<Bill>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Bill bill = new Bill();
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    bill.setType(jsonObject1.getString("type"));
                                    bill.setUpload(1);
                                    bill.setMoney(jsonObject1.getString("money"));
                                    if (!TextUtils.isEmpty(jsonObject1.getString("remark"))) {
                                        bill.setRemark(jsonObject1.getString("remark"));
                                    }
                                    bill.setTime(jsonObject1.getString("update_time"));
                                    bills.add(bill);
                                }
                                onGetAllBillListFromServer.onSuccess(bills);
                            } else {
                                onGetAllBillListFromServer.onFail("该用户暂无账单信息");
                            }
                        }
                    } catch (JSONException e) {
                        onGetAllBillListFromServer.onFail("获取账单数据失败");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onGetAllBillListFromServer.onFail("获取账单数据失败");
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


    //后台获取预判账单的回掉接口
    public interface OnGetForecastFromServer {
        void onSuccess(List<BillType> billTypes);

        void onFail();
    }

    //后台获取所有账单信息的回掉接口

    public interface OnGetAllBillListFromServer {
        void onSuccess(List<Bill> bills);

        void onFail(String error);
    }
}
