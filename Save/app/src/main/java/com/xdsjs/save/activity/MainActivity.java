package com.xdsjs.save.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.xdsjs.save.R;
import com.xdsjs.save.utils.ActivityManager;
import com.xdsjs.save.utils.KeyBoardUtils;


public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private EditText etPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwd.requestFocus();
        KeyBoardUtils.openKeybord(etPwd,this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().killAllActivity();
    }
}
