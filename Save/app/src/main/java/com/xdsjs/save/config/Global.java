package com.xdsjs.save.config;

import android.graphics.Color;

/**
 * 这里保存一些全局的参数
 * Created by xdsjs on 2015/10/14.
 */
public class Global {
    /**
     * 记账类型的数组
     */
    public static final String[] types = new String[]{
            "个人收入", "早午晚餐", "公共交通", "水果零食",
            "衣服鞋帽", "日常用品", "休闲聚餐", "运动健身",
            "网上购物", "打车租车", "交流通讯", "旅游度假",
            "化妆饰品", "数码设备", "通讯上网", "邮寄快递",
            "宠物宝贝", "水电煤气", "学习培训", "其他杂项"
    };

    public static final int[] colors = new int[]{
            Color.rgb(205, 205, 205), Color.rgb(114, 188, 223), Color.rgb(255, 123, 124),
            Color.rgb(57, 135, 200), Color.rgb(50, 205, 205), Color.rgb(29, 58, 196),
            Color.rgb(96, 856, 205), Color.rgb(89, 152, 86), Color.rgb(160, 55, 185),
            Color.rgb(55, 88, 99), Color.rgb(99, 88, 55), Color.rgb(88, 77, 33),
            Color.rgb(111, 111, 55), Color.rgb(150, 122, 258), Color.rgb(59, 86, 128),
            Color.rgb(55, 258, 134), Color.rgb(25, 150, 198), Color.rgb(75, 55, 205),
            Color.rgb(200, 55, 55), Color.rgb(88, 200, 77)
    };
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
    public static final String NETWORK_URL = "http://192.168.1.100:8080/";//接口地址前缀

    public static final String NETWORK_ACTION_LOGIN = NETWORK_URL + "login";//登陆
    public static final String NETWORK_ACTION_REGIST = NETWORK_URL + "insert";//注册
    public static final String NETWORK_ACTION_GET_USER_PERSONAL_INFO = NETWORK_URL + "getUserMessage";//获取用户个人信息
    public static final String NETWORK_ACTION_UPLOAD_USER_INFO = NETWORK_URL + "updateUserMessage";//上传用户信息
    public static final String NETWORK_ACTION_GET_USER_BILL_INFO = NETWORK_URL + "getBillsMessage";//获取用户账单信息
    public static final String NETWORK_ACTION_GET_FORECAST_TYPE = NETWORK_URL + "getMaxType";//获取预判的种类

}
