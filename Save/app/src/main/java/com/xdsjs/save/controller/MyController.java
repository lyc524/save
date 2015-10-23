package com.xdsjs.save.controller;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xdsjs.save.application.MyApplication;
import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.model.BaseModel;
import com.xdsjs.save.model.MyModel;
import com.xdsjs.save.network.HttpUtils;
import com.xdsjs.save.utils.NetUtils;
import com.xdsjs.save.utils.TimeUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    private List<Bill> allBills;//所有的账单信息
    private List<Bill> weekBills;//一周内的账单信息
    private List<Bill> monthBills;//一月内的账单信息
    private List<Bill> yearBills;//一年内的账单信息
    private List<BillType> billTypes;//排好序的预判账单

    private boolean isBillChange = false;//标记一个应用生命周期内账单信息是否发生了变化

    //保存账单list
    public void saveBillList(List<Bill> bills) {
        if (allBills == null) {
            this.allBills = bills;
            getMyModel().saveBillList(allBills);
        }
    }

    //保存单个账单记录
    public void saveBill(Bill bill) {
        isBillChange = true;
        getMyModel().saveBill(bill);
    }

    //根据日期获取一周内账单List
    public List<Bill> getWeekBillList() {
        if (weekBills != null && !isBillChange) {
            return weekBills;
        }
        weekBills = getMyModel().getBillList(TimeUtils.getFirstDayTimeOfWeek());
        return weekBills;
    }

    //根据日期获取一月内账单List
    public List<Bill> getMonthBillList() {
        if (monthBills != null && !isBillChange) {
            return monthBills;
        }
        monthBills = getMyModel().getBillList(TimeUtils.getFirstDayTimeOfMonth());
        return monthBills;
    }

    //根据日期获取一年内账单List
    public List<Bill> getYearBillList() {
        if (yearBills != null && !isBillChange) {
            return yearBills;
        }
        yearBills = getMyModel().getBillList(TimeUtils.getFirstDayTimeOfYear());
        return yearBills;
    }

    //获取未更新到服务器的账单
    public List<Bill> getUnUploadBillList() {
        return getMyModel().getUnUploadBillList();
    }

    //根据当前时间段更新次数表
    public void updateTime(BillType billType) {
        getMyModel().updateTime(billType);
    }

    //根据当前时间段获取排好序的次数表
    public List<BillType> getBillTypeList(Context context) {
        if (billTypes == null) {
            billTypes = getMyModel().getBillTypeList();
        }
        //如果当前有网络连接且用户登录的情况下，则进行后台查询
        if (NetUtils.isConnected(context) && ((MyController) BaseController.getInstance()).getMyModel().getPersonalAutoLogin()) {
            getForecastFormServer(context, billTypes, new OnGetForecastFromServer() {
                @Override
                public void onSuccess(List<BillType> billTypess) {
                    Log.e("LoginActivity--------->", billTypess.toString());
                    billTypes = billTypess;
                    BillType billType = new BillType();
                    for (int i = 0; i < billTypes.size(); i++) {
                        if (billTypes.get(i).getType().equals("type_0")) {
                            billType = billTypes.get(i);
                            billTypes.remove(i);
                            break;
                        }
                    }
                    billTypes.add(0, billType);
                    noitifyRemarkSyncListeners(billTypes);
                }

                @Override
                public void onFail() {
                    Log.e("-------------->", "获取后台预判数据失败");
                }
            });
        }
        //将收入项放到第一个
        BillType billType = new BillType();
        for (int i = 0; i < billTypes.size(); i++) {
            if (billTypes.get(i).getType().equals("type_0")) {
                billType = billTypes.get(i);
                billTypes.remove(i);
                break;
            }
        }
        billTypes.add(0, billType);
        return billTypes;
    }

    /**
     * 获取用户账单信息
     * 每当用户非自动登陆情况下都要调用
     */
    public void getBillListFromServer() {
        getBillListFormServer(new OnGetAllBillListFromServer() {
            @Override
            public void onSuccess(List<Bill> bills) {
                saveBillList(bills);
            }

            @Override
            public void onFail(String error) {
                Log.e("MyController--->", error);
            }
        });
    }


    /**
     * 向后台上传用户信息,包括为更新的账单，总支出和总收入
     * 条件为有网络连接，且用户已登录，且用户设置了自动上传
     */
    public void updateBillListToServer() {
        if (NetUtils.isConnected(MyApplication.getInstance()) && getMyModel().getPersonalAutoLogin() && getMyModel().getSettingUpLoad()) {
            List<Bill> bills = getUnUploadBillList();
            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", getMyModel().getPersonalAccount());
                jsonObject.put("pay_money_bef", getMyModel().getPersonalTotalOut());
                jsonObject.put("get_money_bef", getMyModel().getPersonalTotalIn());
                JSONArray jsonArray = new JSONArray();
                JSONObject json;
                for (Bill bill : bills) {
                    json = new JSONObject();
                    json.put("type", bill.getType());
                    json.put("money", bill.getMoney());
                    json.put("remark", bill.getRemark());
                    json.put("update_time", bill.getTime());
                    jsonArray.put(json);
                }
                jsonObject.put("bills", jsonArray);
                params.put("json", jsonObject.toString());
                HttpUtils.post(Global.NETWORK_ACTION_UPLOAD_USER_INFO, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject respose = new JSONObject(new String(responseBody));
                            if (respose.getString("result").equals("1")) {
                                Log.e("MyController--->", "上传账单信息成功");
                            } else {
                                Log.e("MyController--->", "上传账单信息失败");
                            }
                        } catch (JSONException e) {
                            Log.e("MyController--->", "上传账单信息失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("MyController--->", "上传账单信息失败");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
