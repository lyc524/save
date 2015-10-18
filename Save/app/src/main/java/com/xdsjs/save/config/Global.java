package com.xdsjs.save.config;

/**
 * 这里保存一些全局的参数
 * Created by xdsjs on 2015/10/14.
 */
public class Global {
    /**
     * 要缓存的设置信息
     */
    public static final String SHARE_SETTING_UPLOAD = "setting_upload";//设置是否上传
    public static final String SHARE_SETTING_BILL_PWD = "setting_bill_pwd";//设置是否需要设置安全密码
    public static final String SHARE_SETTING_BUDGET = "setting_budget";//设置预算
    /**
     * 要缓存的个人信息
     */
    public static final String SHARE_PERSONAL_ACCOUNT = "personal_account";//个人账号
    public static final String SHARE_PERSONAL_PWD = "personal_pwd";//个人密码
    public static final String SHARE_PERSONAL_BILL_PWD = "personal_bill_pwd";//个人安全密码
    public static final String SHARE_PERSONAL_TOTAL_IN = "personal_total_in";//个人历史总收入
    public static final String SHARE_PERSONAL_TOTAL_OUT = "personal_total_out";//个人历史总支出

    public static final String SHARE_PERSONAL_AUTO_LOGIN = "personal_auto_login";//标记是否自动登录
    /**
     * 接口信息
     */
    public static final String NETWORK_URL = "http://192.168.0.0:8080/save/";//接口地址前缀

    public static final String NETWORK_ACTION_LOGIN = NETWORK_URL + "login";//登陆
    public static final String NETWORK_ACTION_REGIST = NETWORK_URL + "insert";//注册
    public static final String NETWORK_ACTION_GET_USER_PERSONAL_INFO = NETWORK_URL + "getUserMessage";//获取用户个人信息
    public static final String NETWORK_ACTION_UPLOAD_USER_INFO = NETWORK_URL + "updateUserMessage";//上传用户信息
    public static final String NETWORK_ACTION_GET_USER_BILL_INFO = NETWORK_URL + "getBillsMessage";//获取用户账单信息
    public static final String NETWORK_ACTION_GET_FORECAST_TYPE = NETWORK_URL + "getMaxType";//获取预判的种类

}
