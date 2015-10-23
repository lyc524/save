package com.xdsjs.save.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xdsjs.save.R;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.network.HttpUtils;
import com.xdsjs.save.utils.SPUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistActivity extends BaseActivity {

    private EditText etAccount, etPwd, etPwdAgain;
    private String account, pwd, pwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwdAgain = (EditText) findViewById(R.id.et_pwd_again);
    }

    public void confirm(View view) {
        if (checkLogin()) {
            doLogin(account, pwd, pwdAgain);
        } else {
            return;
        }
    }

    private boolean checkLogin() {
        account = etAccount.getText().toString();
        pwd = etPwd.getText().toString();
        pwdAgain = etPwdAgain.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showBottomToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showBottomToast("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            showBottomToast("请再次输入密码");
            return false;
        }
        if (pwd.equals(pwdAgain)) {
            return true;
        }
        return true;
    }

    private void doLogin(final String account, final String pwd, String pwdAgain) {
        showLodingDialog(this);
        RequestParams params = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", account);
            jsonObject.put("password", pwd);
            jsonObject.put("password_sec", pwdAgain);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json", jsonObject.toString());
        HttpUtils.post(Global.NETWORK_ACTION_REGIST, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dismissDialog();
                if (statusCode == 200) {
                    String responce = new String(responseBody);
                    Log.e("RegistActivity-------->", responce);
                    try {
                        JSONObject jsonObject = new JSONObject(responce);
                        String responseCode = jsonObject.getString("result");
                        if (responseCode.equals("1")) {
                            showBottomToast("注册成功");
                            //缓存账号和密码
                            SPUtils.put(RegistActivity.this, Global.SHARE_PERSONAL_ACCOUNT, account);
                            SPUtils.put(RegistActivity.this, Global.SHARE_PERSONAL_PWD, pwd);
                            SPUtils.put(RegistActivity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, true);
                            openActivity(MainActivity.class);
                            RegistActivity.this.finish();
                        } else if (responseCode.equals("0")) {
                            showBottomToast("用户名重复");
                        } else if (responseCode.equals("-1")) {
                            showBottomToast("注册失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dismissDialog();
                error.printStackTrace();
            }
        });
    }
}
