package com.wecanstudio.xdsjs.save.View.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.ViewModel.LoginViewModel;
import com.wecanstudio.xdsjs.save.databinding.FragmentLoginBinding;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class LoginFragment extends BaseFragment<LoginViewModel, FragmentLoginBinding> {

    private String account;
    private String pwd;
    private OnClickedListener onClickedListener;

    public LoginFragment(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setViewModel(new LoginViewModel(this));
        setBinding(DataBindingUtil.<FragmentLoginBinding>inflate(inflater, R.layout.fragment_login, container, false));
        getBinding().setLoginViewModel(getViewModel());
        return getBinding().getRoot();
    }

    public void confirm(View view) {
        if (checkLogin())
            getViewModel().doLogin(account, pwd);
        else
            return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedListener.onRegistClicked();
            }
        });
    }

    private boolean checkLogin() {
        account = getBinding().etAccount.getText().toString();
        pwd = getBinding().etPwd.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showBottomToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showBottomToast("请输入密码");
            return false;
        }
        return true;
    }

    public interface OnClickedListener {
        void onRegistClicked();
    }

}
