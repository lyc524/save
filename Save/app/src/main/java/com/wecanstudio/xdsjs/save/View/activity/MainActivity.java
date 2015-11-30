package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ActivityManager;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionPagerAdapter;
import com.wecanstudio.xdsjs.save.View.fragment.CheckBillPwdDialog;
import com.wecanstudio.xdsjs.save.View.fragment.LoginDialogFragment;
import com.wecanstudio.xdsjs.save.ViewModel.MainPageViewModel;
import com.wecanstudio.xdsjs.save.ViewModel.UserInfoViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityMainBinding;
import com.wecanstudio.xdsjs.save.databinding.NavHeaderMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<MainPageViewModel, ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener, LoginDialogFragment.LoginListener, CheckBillPwdDialog.CheckBillPwdListener {

    private ViewPager viewPager;
    private LoginDialogFragment loginDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new MainPageViewModel(this));
        setBinding(DataBindingUtil.<ActivityMainBinding>setContentView(this, R.layout.activity_main));
        getBinding().setMainPageModel(getViewModel());
        initView();
    }

    private void initView() {
        //初始化toolbar和drawerlayout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, getBinding().drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getBinding().drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        getBinding().navView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("记一笔");
        //完成对HeaderView的绑定
        NavHeaderMainBinding navBind = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, null, false);
        getBinding().navView.addHeaderView(navBind.getRoot());
        navBind.setUserInfoViewModel(new UserInfoViewModel());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initTypeShow();
        //设置预判的记账类型
        getViewModel().setDefaultType();
    }

    /*
    初始化viewpager+gridView
     */
    private void initTypeShow() {
        final List<View> views = new ArrayList<>();

        views.add(getViewModel().getGridChildView(1));
        views.add(getViewModel().getGridChildView(2));
        views.add(getViewModel().getGridChildView(3));

        viewPager.setAdapter(new ExpressionPagerAdapter(views));

        viewPager.setOnPageChangeListener(getViewModel().getOnPagerChangeListener());
        getViewModel().setCurDial(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().killAllActivity();
    }

    /**
     * 头像的点击事件处理
     *
     * @param view
     */
    public void onAvatarClicked(View view) {
        if ((Boolean) SPUtils.get(this, Global.SHARE_PERSONAL_AUTO_LOGIN, false))
            openActivity(UserInfoActivity.class);
        else {
            loginDialogFragment = new LoginDialogFragment();
            loginDialogFragment.show(getFragmentManager(), "loginDialog");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_billInfo) {
            if (SPModel.getSettingBillPwd()) {
                CheckBillPwdDialog billPwdDialog = new CheckBillPwdDialog(this, SPModel.getPersonalBillPwd());
                billPwdDialog.show(getFragmentManager(), "dialogBill");
            } else
                openActivity(BillInfoActivity.class);
        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_setting) {
            openActivity(SettingActivity.class);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoginSucceed() {
        if (loginDialogFragment != null && loginDialogFragment.isVisible())
            loginDialogFragment.dismiss();
        getViewModel().setDefaultType();
    }

    @Override
    public void onBillPwdCheckSuccess() {
        openActivity(BillInfoActivity.class);
    }

    @Override
    public void onBillPwdCheckFailed() {

    }
}
