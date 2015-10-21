package com.xdsjs.save.model;

import android.content.Context;

import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.db.BillTableDao;
import com.xdsjs.save.db.DBManager;
import com.xdsjs.save.db.TimeDao;

import java.util.List;

/**
 * 提供对数据库的操作
 * Created by xdsjs on 2015/10/14.
 */
public class MyModel extends DefaultModel {


    public MyModel(Context context) {
        super(context);
    }

    //保存账单list
    public void saveBillList(List<Bill> bills) {
        BillTableDao billTableDao = new BillTableDao(context);
        billTableDao.saveBillList(bills);
    }

    //保存单个记录
    public void saveBill(Bill bill) {
        BillTableDao billTableDao = new BillTableDao(context);
        billTableDao.saveBill(bill);
    }

    //根据日期获取账单List
    public List<Bill> getBillList(long time) {
        BillTableDao billTableDao = new BillTableDao(context);
        return billTableDao.getBillList(time);
    }

    //根据当前时间段更新次数表
    public void updateTime(BillType billType) {
        TimeDao timeDao = new TimeDao(context);
        timeDao.updateTime(billType);
    }

    //根据当前时间段获取排好序的次数表
    public List<BillType> getBillTypeList(){
        TimeDao timeDao = new TimeDao(context);
        return timeDao.getBillTypeList();
    }

    public void closeDB() {
        DBManager.getInstance().closeDB();
    }

    public void logOut() {
        setPersonalAutoLogin(false);
    }
}
