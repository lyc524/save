package com.wecanstudio.xdsjs.save.ViewModel;

import android.view.View;

import com.wecanstudio.xdsjs.save.Model.RegistResponse;
import com.wecanstudio.xdsjs.save.Model.Register;
import com.wecanstudio.xdsjs.save.Model.net.RestApi;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.Utils.NetUtils;
import com.wecanstudio.xdsjs.save.View.fragment.RegistFragment;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class RegistViewModel extends LoadingViewModel {

    private RegistFragment registFragment;

    public RegistViewModel(RegistFragment registFragment) {
        this.registFragment = registFragment;
    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }

    public void doRegist(String account, String pwd) {
        final Register register = new Register(account, pwd, "123456");
        MyApplication.getInstance().createApi(RestApi.class)
                .regist(register)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (NetUtils.isConnected(appContext))
                            registFragment.showMiddleToast("注册失败,该用户已存在");
                        else
                            registFragment.showMiddleToast("请检查网络连接-_-");
                    }

                    @Override
                    public void onNext(RegistResponse response) {
                        switch (response.getStatus()) {
                            case "1":
                                registFragment.showMiddleToast("注册成功,请登录");
                                registFragment.getFragmentManager().popBackStack();
                                break;
                        }
                    }
                });
    }
}
