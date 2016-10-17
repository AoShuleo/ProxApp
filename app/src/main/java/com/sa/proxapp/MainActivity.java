package com.sa.proxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    View.OnClickListener regButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RegActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AppActivity.class);
            startActivity(intent);
        }
    };

    Button reg_button;
    Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg_button = (Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(regButtonListener);

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(loginButtonListener);
    }
}
