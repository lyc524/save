package com.xdsjs.save.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.xdsjs.save.BillInfoFragment;
import com.xdsjs.save.R;

public class BillInfoActivity extends BaseActivity implements View.OnClickListener {

    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;

    private ImageView ivBack;
    private ImageView ivSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);
        initView();
    }

    private void initView() {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmenAdapter(getSupportFragmentManager()));
        initTabsValue();
        tabs.setViewPager(viewPager);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivSetting = (ImageView) findViewById(R.id.iv_set);
        ivBack.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_set:
                break;
        }
    }


    private class FragmenAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"周", "月", "年"};

        public FragmenAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BillInfoFragment.newInstance(position + "");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    //tab标签的配置
    private void initTabsValue() {
        // 底部游标颜色
        tabs.setIndicatorColor(Color.WHITE);
        // tab的分割线颜色
        tabs.setDividerColor(Color.TRANSPARENT);
        // tab背景
        tabs.setBackgroundColor(Color.parseColor("#5bc276"));
        // tab底线高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, getResources().getDisplayMetrics()));
        // 选中的文字颜色
//        tabs.setSelectedTextColor(Color.WHITE);
        // 正常文字颜色
        tabs.setTextColor(Color.WHITE);

        //设置等分
        tabs.setShouldExpand(true);
    }
}
