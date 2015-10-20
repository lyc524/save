package com.xdsjs.save.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xdsjs.save.R;
import com.xdsjs.save.controller.BaseController;
import com.xdsjs.save.controller.MyController;
import com.xdsjs.save.model.MyModel;
import com.xdsjs.save.utils.ActivityManager;
import com.xdsjs.save.utils.DensityUtil;
import com.xdsjs.save.utils.KeyBoardUtils;
import com.xdsjs.save.utils.TimeUtils;
import com.xdsjs.save.widget.PasswordEditText;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout layoutTop;
    private Button btnPersonal;
    private Button btnBillInfo;

    private boolean isPopupWindowShowing = false;//标记popupWindow是否显示

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(TimeUtils.getCurrentTime());
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        layoutTop = (LinearLayout) findViewById(R.id.layout_top);
        btnPersonal = (Button) findViewById(R.id.title_bar_left_menu);
        btnBillInfo = (Button) findViewById(R.id.title_bar_right_menu);
        btnPersonal.setOnClickListener(this);
        btnBillInfo.setOnClickListener(this);

    }

    /**
     * 配置显示popupWindow
     */
    private void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_pwd, null);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 150.0f), true);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.top_title_color));
        popupWindow.setTouchable(false);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopupWindowShowing = false;
            }
        });
        final PasswordEditText etpwd = (PasswordEditText) contentView.findViewById(R.id.et_pwd);
        final TextView tvPwd = (TextView) contentView.findViewById(R.id.tv_pwd);

        popupWindow.showAsDropDown(view);
        KeyBoardUtils.openKeybord(etpwd, MainActivity.this);
        isPopupWindowShowing = true;
        //判断当前用户是否设置了安全密码，如果没有，则引导设置
        final MyModel myModel = ((MyController) BaseController.getInstance()).getMyModel();
        if (myModel.getSettingBillPwd()) {
            if (TextUtils.isEmpty(myModel.getPersonalBillPwd())) {
                Log.e("------------------>", myModel.getPersonalBillPwd());
                etpwd.setCurrentMode(PasswordEditText.MODE_SET_PASSWORD);
            } else {
                etpwd.setCurrentMode(PasswordEditText.MODE_CHECK_PASSWORD);
                etpwd.setPwd(myModel.getPersonalBillPwd());
            }
        }
        etpwd.setOnSetPwdListener(new PasswordEditText.OnSetPwdListener() {
            @Override
            public void onSetPwdFirst() {
                tvPwd.setText("请再次输入密码");
            }

            @Override
            public void onSetPwdFail() {
                tvPwd.setText("请输入密码");
                showBottomToast("两次密码输入不一致,请重新输入");
            }

            @Override
            public void onSetPwdSuccess(String pwd) {
                myModel.setPersonalBillPwd(pwd);
                tvPwd.setText("密码设置成功");
            }
        });
        etpwd.setOnCheckPwdListener(new PasswordEditText.OnCheckPwdListener() {
            @Override
            public void onCheckSuccess() {
                tvPwd.setText("密码输入正确");
            }

            @Override
            public void onCheckFail() {
                tvPwd.setText("请输入密码");
                showBottomToast("密码输入错误,请重新输入");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().killAllActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_left_menu:
                if (((MyController) BaseController.getInstance()).getMyModel().getPersonalAutoLogin()) {
                    openActivity(PersonalInfoActivity.class);
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.title_bar_right_menu:
                if (!isPopupWindowShowing)
                    showPopupWindow(layoutTop);
                break;
        }
    }
}
