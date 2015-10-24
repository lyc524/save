package com.xdsjs.save.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DBManager {
    static private DBManager dbMgr = new DBManager();
    private DbOpenHelper dbHelper;

    void onInit(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
    }

    public static synchronized DBManager getInstance() {
        return dbMgr;
    }


    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();
        }
    }

    //保存账单list
    synchronized void saveBillList(List<Bill> bills) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(BillTableDao.TABLE_NAME, null, null);
            for (Bill bill : bills) {
                ContentValues cv = new ContentValues();
                cv.put(BillTableDao.COLUMN_NAME_TYPE, bill.getType());
                cv.put(BillTableDao.COLUMN_NAME_MONEY, bill.getMoney());
                cv.put(BillTableDao.COLUMN_NAME_REMARK, bill.getRemark());
                cv.put(BillTableDao.COLUMN_NAME_TIME, bill.getTime());
                cv.put(BillTableDao.COLUMN_NAME_UPLOAD, bill.getUpload());
                db.replace(BillTableDao.TABLE_NAME, null, cv);
            }
        }
    }

    //保存单个账单
    synchronized void saveBill(Bill bill) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put(BillTableDao.COLUMN_NAME_TYPE, bill.getType());
            cv.put(BillTableDao.COLUMN_NAME_MONEY, bill.getMoney());
            cv.put(BillTableDao.COLUMN_NAME_REMARK, bill.getRemark());
            cv.put(BillTableDao.COLUMN_NAME_TIME, bill.getTime());
            cv.put(BillTableDao.COLUMN_NAME_UPLOAD, bill.getUpload());
            db.replace(BillTableDao.TABLE_NAME, null, cv);
        }
    }

    //根据日期获取账单
    synchronized List<Bill> getBill(long time) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Bill> bills = new ArrayList<>();
        if (db.isOpen()) {
            Log.e("^^^^^^^^^^^^^", "select * from " + BillTableDao.TABLE_NAME + " where " + BillTableDao.COLUMN_NAME_TIME + " > ?");
            Cursor cursor = db.rawQuery("select * from " + BillTableDao.TABLE_NAME + " where " + BillTableDao.COLUMN_NAME_TIME + " > ?", new String[]{time + ""});
            Bill bill;
            while (cursor.moveToNext()) {
                bill = new Bill();
                bill.setType(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_TYPE)));
                bill.setMoney(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_MONEY)));
                bill.setRemark(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_REMARK)));
                bill.setTime(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_TIME)));
                bill.setUpload(cursor.getInt(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_UPLOAD)));
                bills.add(bill);
            }
        }
        return bills;
    }

    //获取未更新到服务器的账单
    synchronized List<Bill> getUnUploadBillList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Bill> bills = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + BillTableDao.TABLE_NAME + " where " + BillTableDao.COLUMN_NAME_UPLOAD + " = ?", new String[]{"0"});
            Bill bill;
            while (cursor.moveToNext()) {
                bill = new Bill();
                bill.setType(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_MONEY)));
                bill.setMoney(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_MONEY)));
                bill.setRemark(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_REMARK)));
                bill.setTime(cursor.getString(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_TIME)));
                bill.setUpload(cursor.getInt(cursor.getColumnIndex(BillTableDao.COLUMN_NAME_UPLOAD)));
                bills.add(bill);
            }
        }
        return bills;
    }

    //更新次数表
    synchronized void updateTime(BillType billType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put("type_" + billType.getType(), billType.getTime());
            db.update(TimeDao.TABLE_NAME, cv, "time = ?", new String[]{TimeUtils.getCurrentTime() + ""});
        }
    }

    //获取排好序的预判list
    synchronized List<BillType> getBillTypeList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<BillType> billTypes = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TimeDao.TABLE_NAME + " where time = ?", new String[]{TimeUtils.getCurrentTime() + ""});
            BillType billType;
            while (cursor.moveToNext()) {
                for (int i = 0; i < 20; i++) {
                    billType = new BillType();
                    billType.setTime(cursor.getInt(cursor.getColumnIndex("type_" + i)));
                    billType.setType(i + "");
                    billType.setName(Global.types[i]);
                    billTypes.add(billType);
                }
            }
        }
        Collections.sort(billTypes, new Comparator<BillType>() {
            @Override
            public int compare(BillType lhs, BillType rhs) {
                return rhs.getTime() - lhs.getTime();
            }
        });
//        // 计算权重
//        if (!(billTypes.get(0).getTime() == 0)) {
//            int totalTime = 0;
//            for (BillType billType : billTypes) {
//                totalTime += billType.getTime();
//            }
//            for (BillType billType : billTypes) {
//                billType.setWeight(billType.getTime() / (float) totalTime);
//            }
//        }
        Log.e("DBManager 获取数据库预判记账list", billTypes.toString());
        return billTypes;
    }
}