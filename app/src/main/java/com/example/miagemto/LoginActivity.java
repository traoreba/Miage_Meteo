package com.example.miagemto;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    EditText login_form, password_form;
    Button btn_login, btn_sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_form = (EditText) findViewById(R.id.login_form);
        password_form = (EditText) findViewById(R.id.password_form);
        btn_login = (Button) findViewById(R.id.btn_Login);
        btn_sign_in = (Button) findViewById(R.id.btn_create_account);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeBtnLoginClick();
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeBtn_Sign_onClick();
            }
        });
    }

    private void executeBtnLoginClick(){
        /*if (!login_form.getText().toString().equals("assane")){

            String sErrorLogin = getResources().getString(R.string.error_bad_login);
            login_form.setError(sErrorLogin);
            return;
        }

        if (!password_form.getText().toString().equals("Miage19")){

            String sErrorLogin = getResources().getString(R.string.error_bad_login);
            password_form.setError(sErrorLogin);
            return;
        }*/

        startActivity(new Intent(this, MainActivity.class));
    }

    private void executeBtn_Sign_onClick(){
        startActivity(new Intent(this, CreateCount.class));
    }
}

