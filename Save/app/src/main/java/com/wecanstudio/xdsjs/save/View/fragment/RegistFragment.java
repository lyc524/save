package com.wecanstudio.xdsjs.save.View.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.ViewModel.RegistViewModel;
import com.wecanstudio.xdsjs.save.databinding.FragmentRegistBinding;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class RegistFragment extends BaseFragment<RegistViewModel, FragmentRegistBinding> {

    private String account;
    private String pwd, pwdAgain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setViewModel(new RegistViewModel(this));
        setBinding(DataBindingUtil.<FragmentRegistBinding>inflate(inflater, R.layout.fragment_regist, container, false));
        getBinding().setRegistViewModel(getViewModel());
        return getBinding().getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin()) {
                    getViewModel().doRegist(account, pwd);
                } else {
                    return;
                }
            }
        });
    }

    private boolean checkLogin() {
        account = getBinding().etAccount.getText().toString().trim();
        pwd = getBinding().etPwd.getText().toString().trim();
        pwdAgain = getBinding().etPwdAgain.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showBottomToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showBottomToast("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            showBottomToast("请再次输入密码");
            return false;
        }
        if (pwd.equals(pwdAgain)) {
            return true;
        }
        return true;
    }
}
