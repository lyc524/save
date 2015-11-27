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
import android.widget.AdapterView;

import com.wecanstudio.xdsjs.save.Model.BillType;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ActivityManager;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionAdapter;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionPagerAdapter;
import com.wecanstudio.xdsjs.save.View.fragment.LoginDialogFragment;
import com.wecanstudio.xdsjs.save.View.widget.ExpandGridView;
import com.wecanstudio.xdsjs.save.ViewModel.MainPageViewModel;
import com.wecanstudio.xdsjs.save.ViewModel.UserInfoViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityMainBinding;
import com.wecanstudio.xdsjs.save.databinding.NavHeaderMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<MainPageViewModel, ActivityMainBinding> implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, LoginDialogFragment.LoginListener {

    private ViewPager viewPager;
    private List<BillType> billTypes;
    private ExpressionPagerAdapter expressionPagerAdapter;//pager adapter
    private LoginDialogFragment loginDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new MainPageViewModel());
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
        //数据库获取所有的记账类型
        billTypes = getViewModel().getBillTypeListFromDB();
        initTypeShow();
        getViewModel().setDefaultType();
    }

    private void initTypeShow() {
        final List<View> views = new ArrayList<>();

        View view1 = getGridChildView(1);
        View view2 = getGridChildView(2);
        View view3 = getGridChildView(3);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        expressionPagerAdapter = new ExpressionPagerAdapter(views);
        viewPager.setAdapter(expressionPagerAdapter);

        viewPager.setOnPageChangeListener(getViewModel().getOnPagerChangeListener());
        getViewModel().setCurDial(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().killAllActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remark:
                break;
        }
    }

    private View getGridChildView(final int i) {
        View view = View.inflate(this, R.layout.viewpager_item, null);
        final ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);

        final List<BillType> list = new ArrayList<BillType>();
        if (i == 1) {
            list.addAll(billTypes.subList(0, 8));
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
                getViewModel().refresh(list.get(position));
            }
        });
        return view;
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (popupWindow != null && popupWindow.isShowing()) {
//            popupWindow.dismiss();
//            isPopupWindowShowing = false;
//        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_billInfo) {

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_setting) {

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
}
