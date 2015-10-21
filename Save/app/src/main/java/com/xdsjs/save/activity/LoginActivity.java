package com.xdsjs.save.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xdsjs.save.R;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.network.HttpUtils;
import com.xdsjs.save.utils.SPUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etAccount, etPwd;
    private String account, pwd;

    private ImageView ivBack;
    private TextView tvRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        checkAutoLogin();
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvRegist = (TextView) findViewById(R.id.tv_regist);
        ivBack.setOnClickListener(this);
        tvRegist.setOnClickListener(this);
    }

    private void checkAutoLogin() {
        if ((boolean) SPUtils.get(this, Global.SHARE_PERSONAL_AUTO_LOGIN, false)) {
            openActivity(MainActivity.class);
            this.finish();
        }
    }

    public void confirm(View view) {
        if (checkLogin()) {
            if (account.equals("1") && pwd.equals("1")) {
                SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_ACCOUNT, account);
                SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_PWD, pwd);
                SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, true);
                openActivity(MainActivity.class);
                this.finish();
                return;
            }
            doLogin(account, pwd);
        } else {
            return;
        }
    }

    private boolean checkLogin() {
        account = etAccount.getText().toString();
        pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showBottomToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showBottomToast("请输入密码");
            return false;
        }
        return true;
    }

    private void doLogin(final String account, final String pwd) {
        showLodingDialog(this);
        RequestParams params = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", account);
            jsonObject.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json", jsonObject);
        HttpUtils.post(Global.NETWORK_ACTION_LOGIN, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dismissDialog();
                if (statusCode == 200) {
                    String responce = new String(responseBody);
                    try {
                        JSONObject jsonObject = new JSONObject(responce);
                        String responseCode = jsonObject.getString("result");
                        if (responseCode.equals("1")) {
                            showBottomToast("登陆成功");
                            SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_ACCOUNT, account);
                            SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_PWD, pwd);
                            SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, true);
                            LoginActivity.this.finish();
                        } else if (responseCode.equals("0")) {
                            showBottomToast("登陆失败");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                LoginActivity.this.finish();
                break;
            case R.id.tv_regist:
                openActivity(RegistActivity.class);
                break;
        }
    }
}
