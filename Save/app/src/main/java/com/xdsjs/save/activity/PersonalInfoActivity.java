package com.xdsjs.save.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xdsjs.save.R;

/**
 * 个人信息界面
 * Created by xdsjs on 2015/10/17.
 */
public class PersonalInfoActivity extends BaseActivity{

    private TextView tvTotalOut;
    private TextView tvTotalIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
    }
}
