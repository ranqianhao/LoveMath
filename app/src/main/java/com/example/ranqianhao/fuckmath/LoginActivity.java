package com.example.ranqianhao.fuckmath;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.Telephony.Carriers.PASSWORD;

public class LoginActivity extends Activity {

    private EditText mAccount;
    private EditText mPsw;
    private Button mLoginButton;
    private Button mRegisterButton;
    private View loginView;
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private SharedPreferences login_sp;
    private UserDataManager mUserDataManager;
    private boolean userNameAndPswValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //绑定页面控件
        mAccount = (EditText) findViewById(R.id.test_UserName);
        mPsw = (EditText) findViewById(R.id.test_pwd);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mRegisterButton = (Button) findViewById(R.id.ToEdit_btn);

        loginView = findViewById(R.id.login_view);
        loginSuccessView = findViewById(R.id.login_success_view);
        loginSuccessShow = (TextView) findViewById(R.id.login_success_show);

        //getSharedPreferrence()可以用来获取textEdit中键入值，然后填写在另一个editText中
        //这里可以用来设置记住密码，下次自动填写这里用不到。
        login_sp = getSharedPreferences("userInfo", 0);
        String name = login_sp.getString("USER_NAME", "");
        String pwd = login_sp.getString("PASSWORD", "");

        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }
        OnClickListener mListener = new OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ToEdit_btn:                            //登录界面的注册按钮
                        Intent intent_Login_to_Register = new Intent(LoginActivity.this, RegisterActivity.class);    //切换Login Activity至User Activity
                        startActivity(intent_Login_to_Register);
                        finish();
                        break;
                    case R.id.login_btn:
                            login();
                            break;
                }
            }
        };


    private void login() {
        if (isUserNameAndPswValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPsw = mPsw.getText().toString().trim();
            SharedPreferences.Editor editor = login_sp.edit();
            int result = 1;
            // mUserDataManager.findUserByNameAndPsw(userName, userPwd);
            if (result == 1) {                                             //返回1说明用户名和密码均正确
                //保存用户名和密码
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPsw);

                Intent intent = new Intent(LoginActivity.this, UserActivity.class);    //切换Login Activity至User Activity
                startActivity(intent);
                finish();
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();//登录成功提示
            } else if (result != 1) {
                Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }

    }

    public boolean isUserNameAndPswValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPsw.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;
        }
        super.onPause();
    }

}