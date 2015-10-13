package com.xdsjs.save.db;

import android.content.Context;

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

}