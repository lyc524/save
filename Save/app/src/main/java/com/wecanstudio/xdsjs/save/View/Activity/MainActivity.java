package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wecanstudio.xdsjs.save.Model.BillType;
import com.wecanstudio.xdsjs.save.Model.Register;
import com.wecanstudio.xdsjs.save.Model.Response;
import com.wecanstudio.xdsjs.save.Model.User;
import com.wecanstudio.xdsjs.save.Model.db.TimeDao;
import com.wecanstudio.xdsjs.save.Model.net.RestApi;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ActivityManager;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionAdapter;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionPagerAdapter;
import com.wecanstudio.xdsjs.save.View.widget.ExpandGridView;
import com.wecanstudio.xdsjs.save.ViewModel.MainPageViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("易帐");
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //获取预判的记账类型
        TimeDao timeDao = new TimeDao(this);
        Observable.create(subscriber -> timeDao.getBillTypeList())
                .subscribe(s -> Log.e("------->", s.toString()));
        MyApplication.getInstance().createApi(RestApi.class)
                .regist(new Register("sss", "123456", "123456"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response s) {
                    }
                });
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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurDot(position);
                final List<BillType> list = new ArrayList<BillType>();
                ExpressionAdapter expressionAdapter;
                if (position == 0) {
                    list.addAll(billTypes.subList(0, 8));
                } else if (position == 1) {
                    list.addAll(billTypes.subList(8, 16));
                } else if (position == 2) {
                    list.addAll(billTypes.subList(16, billTypes.size()));
                }
                expressionAdapter = new ExpressionAdapter(MainActivity.this, 1, list);
                ((GridView) ((ViewGroup) views.get(position)).getChildAt(0)).setAdapter(expressionAdapter);
                expressionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
            case R.id.remark:
//                new MaterialDialog.Builder(this)
//                        .title("备注")
//                        .inputMaxLength(14)
//                        .input(null, null, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                remarkText = input.toString();
//                            }
//                        }).show();
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
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //保存选中的billtype
                billType = billTypes.get(position);
                for (int j = 0; j < billTypes.size(); j++) {
                    if (j == (8 * (i - 1) + position)) {
                        billTypes.get(j).setIsPressed(true);
                        Log.e("88888888888888888888888", "你点击了---->" + billTypes.get(j).getName());
                    } else {
//                        Log.e("88888888888888888888888", "你点击了---->" + billTypes.get(j).getName());
                        billTypes.get(j).setIsPressed(false);
                    }
                }
                for (int j = 0; j < parent.getCount(); j++) {
                    View v = parent.getChildAt(j);
                    if (position == j) {
                        //根据资源名称获取ID
                        int resId = MainActivity.this.getResources().getIdentifier("type_" + list.get(position).getType() + "_press", "drawable", MainActivity.this.getPackageName());
                        ((ImageView) ((ViewGroup) view).getChildAt(0)).setImageResource(resId);
                    } else {
                        int resId = MainActivity.this.getResources().getIdentifier("type_" + list.get(j).getType() + "_normal", "drawable", MainActivity.this.getPackageName());
                        ((ImageView) ((ViewGroup) v).getChildAt(0)).setImageResource(resId);
                    }
                }
            }
        });
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
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
