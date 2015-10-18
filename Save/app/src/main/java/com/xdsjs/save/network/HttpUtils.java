package com.xdsjs.save.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 对AsyncHttp的简单封装
 * Created by xdsjs on 2015/10/18.
 */
public class HttpUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();//实例话对象

    static {
        client.setTimeout(11000);//设置链接超时，如果不设置，默认为10s
    }

    public static void post(String urlString, AsyncHttpResponseHandler res)//用一个完整url获取一个string对象
    {
        client.post(urlString, res);
    }

    public static void post(String urlString, RequestParams params, AsyncHttpResponseHandler res)//url里面带参数
    {
        client.post(urlString, params, res);
    }

    public static void post(String urlString, JsonHttpResponseHandler res)//不带参数，获取json对象或者数组
    {
        client.post(urlString, res);
    }

    public static void post(String urlString, RequestParams params, JsonHttpResponseHandler res)//带参数，获取json对象或者数组
    {
        client.post(urlString, params, res);
    }

    public static void post(String uString, BinaryHttpResponseHandler bHandler)//下载数据使用，会返回byte数据
    {
        client.post(uString, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }
}
