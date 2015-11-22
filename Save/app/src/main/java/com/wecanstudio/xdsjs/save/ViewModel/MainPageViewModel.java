package com.wecanstudio.xdsjs.save.ViewModel;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.wecanstudio.xdsjs.save.Model.Global;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.MyApplication;

/**
 * Created by xdsjs on 2015/11/18.
 */
public class MainPageViewModel extends LoadingViewModel {

    private StringBuffer totalBuffer = new StringBuffer();
    private StringBuffer stringBuffer = new StringBuffer();
    public final ObservableField<String> totalMoney = new ObservableField<>();
    public final ObservableField<String> total = new ObservableField<>();

    @Command
    public void onLeftBarClicked(View view) {
        if ((boolean) SPUtils.get(MyApplication.getContext(), Global.SHARE_PERSONAL_AUTO_LOGIN, false)) {
            //跳转到个人信息界面
        } else {
            //跳转到登录注册界面
        }
    }

    @Command
    public void onRightBarClicked(View view) {

    }

    @Command
    public void onNumKeyClicked(View view) {
        String tag = view.getTag().toString();
        if ((tag.equals("0") || tag.equals(".") || tag.equals("+")) && stringBuffer.length() == 0)
            return;
        if (stringBuffer.length() != 0 && tag.equals("+")) {
            if (stringBuffer.charAt(stringBuffer.length() - 1) == '+') {
                return;
            } else {
                stringBuffer = new StringBuffer();
            }
        }
        if (stringBuffer.length() != 0 && tag.equals(".")) {
            if (stringBuffer.charAt(stringBuffer.length() - 1) == '.') {
                return;
            }
        }
        stringBuffer.append(tag);
        totalBuffer.append(tag);
        total.set(totalBuffer.toString());
        totalMoney.set(calculate(totalBuffer));
    }

    @Command
    public void onDeleteKeyClicked(View view) {
        if (totalBuffer.length() == 0)
            return;
        if (stringBuffer.length() != 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        totalBuffer.deleteCharAt(totalBuffer.length() - 1);
        totalMoney.set(totalBuffer.length() == 0 ? "0.0" : calculate(totalBuffer));
        total.set(totalBuffer.length() == 0 ? "0.0" : totalBuffer.toString());
    }

    @Command
    public void onClearKeyClicked(View view) {
        totalBuffer = new StringBuffer();
        stringBuffer = new StringBuffer();
        total.set("0.0");
        totalMoney.set("0.0");
    }

    @Command
    public void onPlusKeyClicked(View view) {
        String tag = view.getTag().toString();
        if (tag.equals("+") && totalBuffer.length() == 0)
            return;
        totalBuffer.append(tag);
    }

    @Command
    public void onSubmitKeyClicked(View view) {

    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }

    private String calculate(StringBuffer stringBuffer) {
        if (!stringBuffer.toString().contains("+"))
            return stringBuffer.toString();
        String num = new String(stringBuffer);
        String[] nums = num.split("\\+");
        float total = 0f;
        for (int i = 0; i < nums.length; i++) {
            total += Float.valueOf(nums[i]);
        }
        return String.valueOf(total);
    }
}
