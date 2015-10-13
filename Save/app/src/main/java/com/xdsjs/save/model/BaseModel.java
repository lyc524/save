package com.xdsjs.save.model;

/**
 * model层基类
 * Created by xdsjs on 2015/10/14.
 */
public abstract class BaseModel {
    //设置是否云备份账单数据
    public abstract void setSettingUpLoad(boolean isUpLoad);

    public abstract void getSettingUpLoad();

    //设置查看账单是否需要密码
    public abstract void setSettingBillPwd(boolean isBillPwd);

    public abstract void getSettingBillPwd();
}
