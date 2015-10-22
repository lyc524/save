package com.xdsjs.save.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xdsjs.save.R;
import com.xdsjs.save.utils.ActivityManager;
import com.xdsjs.save.widget.SingleToast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 所有activity的基类
 * Created by xdsjs on 2015/9/29.
 */
public class BaseActivity extends AppCompatActivity {

    private SingleToast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        toast = new SingleToast(this);
    }

    //吐司
    protected void showBottomToast(String msg) {
        toast.showButtomToast(msg);
    }

    protected void showMiddleToast(String msg) {
        toast.showMiddleToast(msg);
    }

    protected void showNetErrorToast() {
        toast.showButtomToast(getString(R.string.internet_failed));
    }

    SweetAlertDialog pDialog;

    /**
     * 显示loding对话框
     *
     * @param context
     */
    protected void showLodingDialog(Context context) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * 展示信息的对话框
     *
     * @param context
     * @param content
     * @param onSweetClickListener
     */
    protected void showDialog(Context context, String content, SweetAlertDialog.OnSweetClickListener onSweetClickListener) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.setTitleText(content);
        pDialog.setConfirmClickListener(onSweetClickListener);
        pDialog.setCancelText("cancle");
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dismissDialog();
            }
        });
        pDialog.show();
    }

    protected void dismissDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }
}
