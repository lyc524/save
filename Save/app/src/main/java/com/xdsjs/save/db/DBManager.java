package com.xdsjs.save.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xdsjs.save.bean.Bill;

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

    //根据日期获取单个账单
    synchronized void getBill(long time) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + BillTableDao.TABLE_NAME + " where " + BillTableDao.COLUMN_NAME_TIME + " < " + time, null);
        }
    }


}