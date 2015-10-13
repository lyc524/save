package com.xdsjs.save.activity;

import android.os.Bundle;

import com.xdsjs.save.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDialog(this, "Hello World", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                showBottomToast("confirm");
            }
        });
//        showLodingDialog(this);
    }
}
