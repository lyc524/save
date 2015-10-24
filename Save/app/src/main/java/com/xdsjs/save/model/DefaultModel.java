package com.xdsjs.save.model;

import android.content.Context;

import com.xdsjs.save.application.MyApplication;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.utils.SPUtils;

/**
 * 默认的设置
 * Created by xdsjs on 2015/10/14.
 */
public class DefaultModel extends BaseModel {

    Context context;

    public DefaultModel(Context context) {
        this.context = context;
    }

    /**
     * 对配置信息的一些缓存
     */
    @Override
    public void setSettingUpLoad(boolean isUpLoad) {
        SPUtils.put(context, Global.SHARE_SETTING_UPLOAD, isUpLoad);
    }

    @Override
    public boolean getSettingUpLoad() {
        return (boolean) SPUtils.get(context, Global.SHARE_SETTING_UPLOAD, true);
    }

    @Override
    public void setSettingBillPwd(boolean isBillPwd) {
        SPUtils.get(context, Global.SHARE_SETTING_BILL_PWD, isBillPwd);
    }

    @Override
    public boolean getSettingBillPwd() {
        return (boolean) SPUtils.get(context, Global.SHARE_SETTING_BILL_PWD, true);
    }

    public void setSettingBudget(float budget) {
        SPUtils.put(context, Global.SHARE_SETTING_BUDGET, budget);
    }

    public float getSettingBudget() {
        return (float) SPUtils.get(context, Global.SHARE_SETTING_BUDGET, 0f);
    }

    /**
     * 对用户数据的缓存
     */
    public void setPersonalAccount(String account) {
        SPUtils.put(context, Global.SHARE_PERSONAL_ACCOUNT, account);
    }

    public String getPersonalAccount() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_ACCOUNT, "");
    }

    public void setPersonalPwd(String pwd) {
        SPUtils.put(context, Global.SHARE_PERSONAL_PWD, pwd);
    }

    public String getPersonalPwd() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_PWD, "");
    }

    public void setPersonalBillPwd(String billPwd) {
        SPUtils.put(context, Global.SHARE_PERSONAL_BILL_PWD, billPwd);
    }

    public String getPersonalBillPwd() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_BILL_PWD, "");
    }

    public void setPersonalTotalIn(String totalIn) {
        SPUtils.put(context, Global.SHARE_PERSONAL_TOTAL_IN, totalIn);
    }

    public String getPersonalTotalIn() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_TOTAL_IN, "0");
    }

    public void setPersonalTotalOut(String totalOut) {
        SPUtils.get(context, Global.SHARE_PERSONAL_TOTAL_OUT, totalOut);
    }

    public String getPersonalTotalOut() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_TOTAL_OUT, "0");
    }

    public void setPersonalAutoLogin(boolean autoLogin) {
        SPUtils.put(context, Global.SHARE_PERSONAL_AUTO_LOGIN, autoLogin);
    }

    public boolean getPersonalAutoLogin() {
        return (boolean) SPUtils.get(context, Global.SHARE_PERSONAL_AUTO_LOGIN, false);
    }
}
