package com.wecanstudio.xdsjs.save.ViewModel;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wecanstudio.xdsjs.save.R;

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
    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }
}
