package com.xdsjs.save.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdsjs.save.R;
import com.xdsjs.save.controller.BaseController;
import com.xdsjs.save.controller.MyController;
import com.xdsjs.save.model.MyModel;

/**
 * 个人信息界面
 * Created by xdsjs on 2015/10/17.
 */
public class PersonalInfoActivity extends BaseActivity {

    private TextView tvTotalOut;
    private TextView tvTotalIn;

    private MyModel myModel;

    private ImageView ivSet;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initView();
    }

    private void initView() {
        tvTotalIn = (TextView) findViewById(R.id.tvTotalIn);
        tvTotalOut = (TextView) findViewById(R.id.tvTotalOut);
        ivSet = (ImageView) findViewById(R.id.iv_set);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        myModel = ((MyController) BaseController.getInstance()).getMyModel();
        Log.e("***************",myModel.getPersonalTotalOut());
        tvTotalOut.setText(myModel.getPersonalTotalOut());
        tvTotalIn.setText(myModel.getPersonalTotalIn());

        ivSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SettingActivity.class);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalInfoActivity.this.finish();
            }
        });
    }
}
