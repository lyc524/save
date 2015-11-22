package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wecanstudio.xdsjs.save.Model.BillType;
import com.wecanstudio.xdsjs.save.Model.db.TimeDao;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ActivityManager;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionAdapter;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionPagerAdapter;
import com.wecanstudio.xdsjs.save.View.widget.ExpandGridView;
import com.wecanstudio.xdsjs.save.ViewModel.MainPageViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends BaseActivity<MainPageViewModel, ActivityMainBinding> implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private List<BillType> billTypes;
    private ExpressionPagerAdapter expressionPagerAdapter;//pager adapter
    private ExpressionAdapter expressionAdapter; //gridview adapter
    private boolean isPopupWindowShowing = false;//标记popupWindow是否显示
    //被选中的类型
    private BillType billType = null;
    private String money;
    private String remarkText;

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("记一笔");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //数据库获取所有的记账类型
        TimeDao timeDao = new TimeDao(this);
        Observable.create(subscriber -> subscriber.onNext(timeDao.getBillTypeList()))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> billTypes = (List<BillType>) s);
//        initTypeShow();
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

        initPoint();
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

    /**
     * 获取表情的gridview的子view
     */
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
        return view;
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

    // 底部小点的图片
    private ImageView[] points;
    // 记录当前选中位置
    private int currentIndex;

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        points = new ImageView[3];

        // 循环取得小点图片
        for (int i = 0; i < 3; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) linearLayout.getChildAt(i);
            // 默认都设为灰色
            points[i].setEnabled(true);
            // 给每个小点设置监听
            points[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        // 排除异常情况
        if (positon < 0 || positon > 2 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);
        currentIndex = positon;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_billinfo) {
            showMiddleToast("billInfo");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
