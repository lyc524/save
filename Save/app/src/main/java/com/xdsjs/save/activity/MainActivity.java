package com.xdsjs.save.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xdsjs.save.R;
import com.xdsjs.save.adapter.ExpressionAdapter;
import com.xdsjs.save.adapter.ExpressionPagerAdapter;
import com.xdsjs.save.bean.BillType;
import com.xdsjs.save.controller.BaseController;
import com.xdsjs.save.controller.MyController;
import com.xdsjs.save.model.MyModel;
import com.xdsjs.save.utils.ActivityManager;
import com.xdsjs.save.utils.DensityUtil;
import com.xdsjs.save.utils.KeyBoardUtils;
import com.xdsjs.save.utils.TimeUtils;
import com.xdsjs.save.widget.ExpandGridView;
import com.xdsjs.save.widget.PasswordEditText;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout layoutTop;
    private Button btnPersonal;
    private Button btnBillInfo;

    private List<BillType> billTypes;

    private ExpressionPagerAdapter expressionPagerAdapter;//pager adapter
    private ExpressionAdapter expressionAdapter; //gridview adapter

    private boolean isPopupWindowShowing = false;//标记popupWindow是否显示

    private RemarkSyncListener remarkSyncListener;

    //键盘相关控件
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive,
            btnSix, btnSeven, btnEight, btnNine, btnZero,
            btnDelete, btnClear, btnDial, btnConfirm, btnPlus;

    private OnKeyBoardClickListener onKeyBoardClickListener;

    //键盘显示
    private TextView tvBigNum, tvSmallNum;

    //维护键盘输入的字符串变量
    private StringBuffer stringBuffer;

    /**
     * 监听后台获取预判账单，成功后刷新页面
     */
    class RemarkSyncListener implements BaseController.SyncListener {

        @Override
        public void onSyncSuccess(final List<BillType> billTypes) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.billTypes = billTypes;
                    initTypeShow();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        layoutTop = (LinearLayout) findViewById(R.id.layout_top);
        btnPersonal = (Button) findViewById(R.id.title_bar_left_menu);
        btnBillInfo = (Button) findViewById(R.id.title_bar_right_menu);
        btnPersonal.setOnClickListener(this);
        btnBillInfo.setOnClickListener(this);
        remarkSyncListener = new RemarkSyncListener();
        //添加监听后台获取预判list的listener
        BaseController.getInstance().addSyncRemarkListener(remarkSyncListener);
        //获取预判的记账类型
        getBillTypes();
        initTypeShow();

        //键盘相关控件的绑定和获取
        btnOne = (Button) findViewById(R.id.btn_one);
        btnTwo = (Button) findViewById(R.id.btn_two);
        btnThree = (Button) findViewById(R.id.btn_three);
        btnFour = (Button) findViewById(R.id.btn_four);
        btnFive = (Button) findViewById(R.id.btn_five);
        btnSix = (Button) findViewById(R.id.btn_six);
        btnSeven = (Button) findViewById(R.id.btn_seven);
        btnEight = (Button) findViewById(R.id.btn_eight);
        btnNine = (Button) findViewById(R.id.btn_nine);
        btnZero = (Button) findViewById(R.id.btn_zero);
        btnDial = (Button) findViewById(R.id.btn_dial);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnPlus = (Button) findViewById(R.id.btn_plus);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        tvBigNum = (TextView) findViewById(R.id.tv_bigNum);
        tvSmallNum = (TextView) findViewById(R.id.tv_smallNum);

        onKeyBoardClickListener = new OnKeyBoardClickListener();
        btnOne.setOnClickListener(onKeyBoardClickListener);
        btnTwo.setOnClickListener(onKeyBoardClickListener);
        btnThree.setOnClickListener(onKeyBoardClickListener);
        btnFour.setOnClickListener(onKeyBoardClickListener);
        btnFive.setOnClickListener(onKeyBoardClickListener);
        btnSix.setOnClickListener(onKeyBoardClickListener);
        btnSeven.setOnClickListener(onKeyBoardClickListener);
        btnEight.setOnClickListener(onKeyBoardClickListener);
        btnNine.setOnClickListener(onKeyBoardClickListener);
        btnZero.setOnClickListener(onKeyBoardClickListener);
        btnDial.setOnClickListener(onKeyBoardClickListener);
        btnDelete.setOnClickListener(onKeyBoardClickListener);
        btnClear.setOnClickListener(onKeyBoardClickListener);
        btnPlus.setOnClickListener(onKeyBoardClickListener);
        btnConfirm.setOnClickListener(onKeyBoardClickListener);

        stringBuffer = new StringBuffer();
    }

    private void initTypeShow() {
        List<View> views = new ArrayList<>();
        View view1 = getGridChildView(1);
        View view2 = getGridChildView(2);
        View view3 = getGridChildView(3);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        expressionPagerAdapter = new ExpressionPagerAdapter(views);
        viewPager.setAdapter(expressionPagerAdapter);
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

    //获取预判的账单list
    private List<BillType> getBillTypes() {
        billTypes = ((MyController) BaseController.getInstance()).getBillTypeList(this);
        if (billTypes == null) {
            billTypes = new ArrayList<>();
        }
        Log.e("MainAct 最终的预判记账list", billTypes.toString());
        return billTypes;
    }

    /**
     * 获取表情的gridview的子view
     */
    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.viewpager_item, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);

        List<BillType> list = new ArrayList<BillType>();
        if (i == 1) {
            List<BillType> list1 = billTypes.subList(0, 8);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(billTypes.subList(8, 16));
        } else if (i == 3) {
            list.addAll(billTypes.subList(16, billTypes.size()));
        }
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
                1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }

    @Override
    protected void onDestroy() {
        if (remarkSyncListener != null) {
            BaseController.getInstance()
                    .removeSyncRemarkListener(remarkSyncListener);
            remarkSyncListener = null;
        }
        super.onDestroy();
    }

    private boolean isDelete = false;//标记是否按下了删除键
    private boolean isPlus = false;//标记是否按下了加法键

    private class OnKeyBoardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_one:
                    stringBuffer.append("1");
                    isPlus = false;
                    break;
                case R.id.btn_two:
                    stringBuffer.append("2");
                    isPlus = false;
                    break;
                case R.id.btn_three:
                    stringBuffer.append("3");
                    isPlus = false;
                    break;
                case R.id.btn_four:
                    stringBuffer.append("4");
                    isPlus = false;
                    break;
                case R.id.btn_five:
                    stringBuffer.append("5");
                    isPlus = false;
                    break;
                case R.id.btn_six:
                    stringBuffer.append("6");
                    isPlus = false;
                    break;
                case R.id.btn_seven:
                    stringBuffer.append("7");
                    isPlus = false;
                    break;
                case R.id.btn_eight:
                    stringBuffer.append("8");
                    isPlus = false;
                    break;
                case R.id.btn_nine:
                    stringBuffer.append("9");
                    isPlus = false;
                    break;
                case R.id.btn_zero:
                    stringBuffer.append("0");
                    isPlus = false;
                    break;
                case R.id.btn_delete:
                    isPlus = false;
                    isDelete = true;
                    break;
                case R.id.btn_dial:
                    stringBuffer.append(".");
                    isPlus = false;
                    break;
                case R.id.btn_clear:
                    stringBuffer = new StringBuffer();
                    isPlus = false;
                    break;
                case R.id.btn_plus:
                    if (!isPlus && stringBuffer.length() > 0) {
                        isPlus = true;
                        stringBuffer.append("+");
                    } else {
                        return;
                    }
                    break;
                case R.id.btn_confirm:
                    return;
            }
            calculate(stringBuffer, isDelete);
        }
    }

    //计算结果并刷新键盘显示界面
    private void calculate(StringBuffer stringBuffer, boolean isDelete) {
        if (stringBuffer.length() > 0 && isDelete) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            this.isDelete = false;
        }
        if (stringBuffer.length() > 0) {
            String num = new String(stringBuffer);
            String[] nums = num.split("\\+");
            float total = 0f;
            for (int i = 0; i < nums.length; i++) {
                total += Float.valueOf(nums[i]);
            }
            tvBigNum.setText(num);
            tvSmallNum.setText(total + "");
        } else {
            tvBigNum.setText("0.0");
            tvSmallNum.setText("0.0");
        }
    }
}
