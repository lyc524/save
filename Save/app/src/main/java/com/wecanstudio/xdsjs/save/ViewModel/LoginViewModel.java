package com.wecanstudio.xdsjs.save.ViewModel;

import android.util.Log;
import android.view.View;

import com.wecanstudio.xdsjs.save.Model.LoginResponse;
import com.wecanstudio.xdsjs.save.Model.User;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.Model.net.RestApi;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.Utils.NetUtils;
import com.wecanstudio.xdsjs.save.View.fragment.LoginFragment;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class LoginViewModel extends LoadingViewModel {

    private LoginFragment loginFragment;

    public LoginViewModel(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }

    /**
     * 处理登陆事件
     *
     * @param account
     * @param pwd
     */
    @Command
    public void doLogin(final String account, final String pwd) {
        User user = new User(account, pwd);
        MyApplication.getInstance().createApi(RestApi.class)
                .login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (NetUtils.isConnected(appContext))
                            loginFragment.showMiddleToast("登陆失败,该用户不存在");
                        else
                            loginFragment.showMiddleToast("请检查网络连接-_-");
                    }

                    @Override
                    public void onNext(LoginResponse response) {
                        if (response.getStatus().equals("1")) {
                            //缓存个人信息
                            SPUtils.put(appContext, Global.SHARE_PERSINAL_TOKEN, response.getAccess_token());
                            SPUtils.put(appContext, Global.SHARE_PERSONAL_ACCOUNT, account);
                            SPUtils.put(appContext, Global.SHARE_PERSONAL_PWD, pwd);
                            loginFragment.getActivity().finish();
                        } else if (response.getStatus().equals("-1")) {
                            Log.e("Login", "该用户不存在");
                            loginFragment.showMiddleToast("该用户不存在");
                        }
                    }
                });
    }
}
