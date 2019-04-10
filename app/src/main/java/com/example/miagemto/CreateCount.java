package com.example.miagemto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateCount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_count);

        final EditText email = findViewById(R.id.email);

        Button createCont =findViewById(R.id.btn_create_account);
        createCont.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainIntent = new Intent(CreateCount.this, MainActivity.class);
                        mainIntent.putExtra(LoginActivity.USER_NAME,email.getText().toString() );
                        startActivity(mainIntent);
                    }
                }
        );
    }
}
