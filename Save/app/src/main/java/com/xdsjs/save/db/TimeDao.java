package com.xdsjs.save.db;

import android.content.Context;

/**
 * 次数表
 * Created by xdsjs on 2015/10/20.
 */
public class TimeDao {
    public static final String TABLE_NAME = "s_time";

    public TimeDao(Context context) {
        DBManager.getInstance().onInit(context);
    }
}
