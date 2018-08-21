package com.example.ranqianhao.fuckmath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private EditText mAccount;
    private EditText mPsw;
    private EditText mCheckPsw;
    private Button mSureBtn;
    private Button mCancelBtn;
    private UserDataManager userDataManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //首先通过组件绑定找到相应的控件
        mAccount = (EditText) findViewById(R.id.register_id);
        mPsw = (EditText)findViewById(R.id.register_pwd);
        mCheckPsw = (EditText)findViewById(R.id.register_pwd_sure);
        mSureBtn = (Button)findViewById(R.id.register_sure);
        mCancelBtn = (Button)findViewById(R.id.register_cancel);

        //注册界面确认注册按钮的监听事件
        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_check();
            }
        });

        //注册界面取消注册按钮的监听事件,,回到注册页面
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Register_to_Login = new Intent(RegisterActivity.this,LoginActivity.class) ;    //切换User Activity至Login Activity
                startActivity(intent_Register_to_Login);
                finish();
            }
        });

        if (userDataManager == null) {
            userDataManager = new UserDataManager(this);
            userDataManager.openDataBase();                              //建立本地数据库
        }


    }

    public void register_check() {

        if(isUserNameAndPswValid()){
              String userName = mAccount.getText().toString().trim();
              String userPsw = mPsw.getText().toString().trim();
              String userPwdCheck = mCheckPsw.getText().toString().trim();
    //检查用户是否存在
    int mCount=UserDataManager.findUserByName(userName);
    if (mCount>1){
        Toast.makeText(this,getString(R.string.name_already_exist),Toast.LENGTH_SHORT).show();
        return;
    }
    //两次输入密码不一致的处理
     if (userPsw.equals(userPwdCheck)==false){
    Toast.makeText(this,getString(R.string.psw_not_the_same),Toast.LENGTH_SHORT).show();
    return;
    } else {
        Userdata userData = new Userdata(userName,userPsw);
        UserDataManager.openDataBase();
        long flag = UserDataManager.insertUserData(userData); //新建用户信息

        if (flag == -1) {
            Toast.makeText(this, getString(R.string.register_fail),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show();
            Intent intent_Register_to_Login = new Intent(RegisterActivity.this,LoginActivity.class) ;    //切换User Activity至Login Activity
            startActivity(intent_Register_to_Login);
            finish();
            }
          }
    }
}

//使用用户名和密码验证用户是否存在
    public boolean isUserNameAndPswValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPsw.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(mCheckPsw.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
