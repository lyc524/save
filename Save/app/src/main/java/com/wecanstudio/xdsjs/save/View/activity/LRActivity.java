package com.wecanstudio.xdsjs.save.View.activity;

import android.os.Bundle;

import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.View.fragment.LoginFragment;
import com.wecanstudio.xdsjs.save.View.fragment.RegistFragment;

/**
 * 进行登录注册
 */
public class LRActivity extends BaseActivity implements LoginFragment.OnClickedListener {

    private LoginFragment loginFragment;
    private RegistFragment registFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lr);
        initView();
    }

    private void initView() {
        loginFragment = new LoginFragment(this);
        addFragment(R.id.content, loginFragment, "login");
    }

    @Override
    public void onRegistClicked() {
        if (registFragment == null)
            registFragment = new RegistFragment();
        replaceFragment(R.id.content, registFragment, "regist");
    }
}
