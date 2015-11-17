package com.wecanstudio.xdsjs.save.Model.net;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL = "http://121.42.209.19:8080/sparkjava";

    String NETWORK_ACTION_LOGIN = API_BASE_URL + "login"; //登陆
    String NETWORK_ACTION_REGIST = API_BASE_URL + "insert"; //注册
    String NETWORK_ACTION_GET_USER_PERSONAL_INFO = API_BASE_URL + "getUserMessage";//获取用户个人信息
    String NETWORK_ACTION_UPLOAD_USER_INFO = API_BASE_URL + "updateUserMessage";//上传用户信息
    String NETWORK_ACTION_GET_USER_BILL_INFO = API_BASE_URL + "getBillsMessage";//获取用户账单信息
    String NETWORK_ACTION_GET_FORECAST_TYPE = API_BASE_URL + "getMaxType";//获取预判的种类
}
