package com.xdsjs.save.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xdsjs.save.R;
import com.xdsjs.save.controller.BaseController;
import com.xdsjs.save.controller.MyController;
import com.xdsjs.save.model.MyModel;
import com.xdsjs.save.widget.ToggleButton;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private ToggleButton tbPwd;
    private RelativeLayout rlChangePwd;
    private RelativeLayout rlAbout;
    private ToggleButton tbUpdate;
    private Button btnLogout;
    private TextView tvBudget;
    private RelativeLayout rlBudget;

    private MyModel myModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tbPwd = (ToggleButton) findViewById(R.id.tb_pwd);
        rlChangePwd = (RelativeLayout) findViewById(R.id.rl_pwd_change);
        rlAbout = (RelativeLayout) findViewById(R.id.rl_about);
        tbUpdate = (ToggleButton) findViewById(R.id.tbUpdate);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        tvBudget = (TextView) findViewById(R.id.tvBudget);
        rlBudget = (RelativeLayout) findViewById(R.id.rl_budget);

        ivBack.setOnClickListener(this);
        rlChangePwd.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        rlBudget.setOnClickListener(this);

        tbPwd.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                myModel.setSettingBillPwd(on);
            }
        });

        tbUpdate.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                myModel.setSettingUpLoad(on);
            }
        });
        myModel = ((MyController) BaseController.getInstance()).getMyModel();
        tbPwd.setToggleOn(myModel.getSettingBillPwd());
        tbUpdate.setToggleOn(myModel.getSettingUpLoad());
        tvBudget.setText(String.valueOf(myModel.getSettingBudget()));

        if (myModel.getPersonalAutoLogin()) {
            btnLogout.setText("退出登陆");
            btnLogout.setBackgroundResource(R.drawable.btn_logout);
        } else {
            btnLogout.setText("登陆");
            btnLogout.setBackgroundResource(R.drawable.btn_login);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.rl_about:
                ((MyController) BaseController.getInstance()).logOut();
                break;
            case R.id.rl_pwd_change:
                break;
            case R.id.rl_budget:
                new MaterialDialog.Builder(this)
                        .title("请输入您的预算")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog materialDialog, final CharSequence charSequence) {
                                if (!TextUtils.isEmpty(charSequence.toString())) {
                                    myModel.setSettingBudget(Float.valueOf(charSequence.toString()));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvBudget.setText(charSequence.toString());
                                        }
                                    });
                                }
                            }
                        }).show();
                break;
            case R.id.btn_logout:
                if (btnLogout.getText().equals("退出登陆")) {
                    btnLogout.setText("登陆");
                    btnLogout.setBackgroundResource(R.drawable.btn_login);
                    ((MyController) BaseController.getInstance()).logOut();
                } else if (btnLogout.getText().equals("登陆")) {
                    openActivity(LoginActivity.class);
                }
                break;
        }
    }
}
