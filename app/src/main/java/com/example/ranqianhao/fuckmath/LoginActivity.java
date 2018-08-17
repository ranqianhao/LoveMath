package com.example.ranqianhao.fuckmath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.login);

        Button button = (Button)findViewById(R.id.ToEdit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Login_to_Register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_Login_to_Register);
                finish();
            }
        });
    }
}