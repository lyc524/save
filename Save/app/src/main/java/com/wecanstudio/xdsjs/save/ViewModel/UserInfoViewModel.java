package com.wecanstudio.xdsjs.save.ViewModel;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;

import com.wecanstudio.xdsjs.save.Model.UserInfo;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.Model.net.RestApi;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.R;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xdsjs on 2015/11/25.
 */
public class UserInfoViewModel extends LoadingViewModel {

    public final ObservableField<Drawable> avatar = new ObservableField<>();//头像

    /**
     * 对变量进行初始化操作
     */
    public void onInit() {
        avatar.set(appContext.getResources().getDrawable(R.drawable.default_avatar));
        //获取个人信息
        MyApplication.getInstance().createApi(RestApi.class)
                .getUserInfo((String) SPUtils.get(appContext, Global.SHARE_PERSINAL_TOKEN, "123"), (String) SPUtils.get(appContext, Global.SHARE_PERSONAL_ACCOUNT, ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        SPUtils.put(appContext, Global.SHARE_PERSONAL_AVATAR, userInfo.getImgurl());
                        Log.e("UserInfoViewModel", "头像地址" + userInfo.getImgurl());
                        avatar.set(appContext.getResources().getDrawable(R.drawable.remark));
                    }
                });
    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }
}
