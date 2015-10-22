package com.xdsjs.save.controller;

import android.content.Context;
import android.util.Log;

import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.model.BaseModel;
import com.xdsjs.save.model.MyModel;
import com.xdsjs.save.utils.NetUtils;
import com.xdsjs.save.utils.TimeUtils;

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
    public List<Bill> getMonthYearList() {
        if (yearBills != null && !isBillChange) {
            return yearBills;
        }
        yearBills = getMyModel().getBillList(TimeUtils.getFirstDayTimeOfYear());
        return yearBills;
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
        //如果当前有网络连接的话，则进行后台查询
        if (NetUtils.isConnected(context)) {
            getForecastFormServer(context, billTypes, new OnGetForecastFromServer() {
                @Override
                public void onSuccess(List<BillType> billTypess) {
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
}
