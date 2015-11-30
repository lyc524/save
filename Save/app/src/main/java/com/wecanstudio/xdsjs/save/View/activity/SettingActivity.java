package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.View.fragment.ChangeBillPwdDialog;
import com.wecanstudio.xdsjs.save.View.fragment.CheckBillPwdDialog;
import com.wecanstudio.xdsjs.save.View.fragment.SetBillPwdDialog;
import com.wecanstudio.xdsjs.save.ViewModel.SettingViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity<SettingViewModel, ActivitySettingBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new SettingViewModel(this));
        setBinding(DataBindingUtil.<ActivitySettingBinding>setContentView(this, R.layout.activity_setting));
        getBinding().setSettingViewModel(getViewModel());
        initView();
    }

    private void initView() {
        setSupportActionBar(getBinding().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("设置");

        getViewModel().initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
