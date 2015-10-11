package com.xdsjs.save.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xdsjs.save.widget.SingleToast;

/**
 * Created by xdsjs on 2015/9/29.
 */
public class BaseActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private SingleToast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = new SingleToast(this);
    }

    //打印日志
    protected void MyLog(String log) {
        Log.i(TAG, log);
    }

    //吐司
    protected void showBottomToast(String msg){
        toast.showButtomToast(msg);
    }

    protected void showMiddleToast(String msg){
        toast.showMiddleToast(msg);
    }
}
