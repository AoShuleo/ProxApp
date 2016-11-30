package com.sa.proxapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import com.sa.proxapp.com.sa.ClientClass.LoginMeListener;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.Report;

public class MainActivity extends AppCompatActivity {

    Button reg_button;
    Button login_button;
    TextView loginField;
    TextView passwordField;

    AlertDialog dialogWaiting;
    AlertDialog dialogError;

    Model model = new Model();


    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            int typeResponse = msg.arg1;
            dialogWaiting.cancel();
            switch (typeResponse)
            {

                case Report.THE_USER_IS_NOT_EXIST: //ошибка, в доступе отказано
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Ошибка аутентификации");
                    builder.setCancelable(false);
                    builder.setMessage("Введите правильную пару логин/пароль или зарегестрируйтесь");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loginField.setText("");
                            passwordField.setText("");

                        }
                    });
                    dialogError = builder.create();
                    dialogError.show();
                    break;
                case Report.SUCCESSFUL_AUTH: //все ок
                    Intent intent = new Intent(MainActivity.this, AppActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };


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

            String login = loginField.getText().toString();
            String password =  passwordField.getText().toString();

            if(login.length() > 0 && password.length() > 0) {
                dialogWaiting.show();
                model.regLoginMeListener(new LoginMeListener(){
                    @Override
                    public void handlerEvent(int typeResponse) {
                        Message message = new Message();
                        message.arg1 = typeResponse;
                        mHandler.sendMessage(message);
                    }
                });
                /*model.regLoginMeListener(new LoginMeListener() {
                    @Override
                    public void handlerEvent(int typeResponse) {

                    }
                });*/

               // for(int i = 0; i < 10000000; ++i)
                    model.loginMe(login, password);
            }
            else
            {
                String mes;
                if(login.length() == 0)
                    mes = "Введите логин";
                else if(password.length() == 0)
                    mes = "Введите пароль";
                else
                    mes = "Введите логин и пароль";
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Ошибка ввода данных");
                builder.setMessage(mes);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Ожидание ответа от сервера");
        dialogBuilder.setTitle("Проверка данных");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(R.layout.dialog_waitig);

        loginField = (TextView)findViewById(R.id.editText2);
        passwordField =(TextView)findViewById(R.id.editText);

        reg_button = (Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(regButtonListener);

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(loginButtonListener);







        dialogBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                model.regLoginMeListener(new LoginMeListener() {
                    @Override
                    public void handlerEvent(int typeResponse) {

                    }
                });
            }
        });

        dialogWaiting = dialogBuilder.create();




    }


}
