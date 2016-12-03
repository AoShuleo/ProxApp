package com.sa.proxapp;

import android.content.DialogInterface;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.RegistrationListener;
import com.sa.proxapp.com.sa.ClientClass.Report;

public class RegActivity extends AppCompatActivity {


    TextView loginField;
    TextView passwordField;
    TextView passwordField2;
    TextView nameUser;
    Button regButton;

    Model model;

    AlertDialog dialogWaiting;


    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            String msgStr = "";
            String titleStr = "";
            dialogWaiting.cancel();
            int typeResponse = msg.arg1;
            switch (typeResponse)
            {
                case Report.THE_USER_EXIST:
                    titleStr = "Ошибка";
                    msgStr = "Пользователь с данным логином уже существует, измените логин";
                    break;
                case Report.SUCCESSFUL_REG:
                    titleStr = "Успешная регистрация";
                    msgStr = "Благодарим за регистрацию в системе PROX!";
                    break;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
            builder.setTitle(titleStr);
            builder.setMessage(msgStr);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();
        }
    };


    View.OnClickListener regButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String loginStr = loginField.getText().toString();
            String nameUserStr = nameUser.getText().toString();
            String pass0 = passwordField.getText().toString();
            String pass1 = passwordField2.getText().toString();

            if(loginStr.length() > 0 &&
                    nameUserStr.length() > 0 &&
                    pass0.length() > 0 &&
                    pass1.length() > 0)
            {

                if(pass0.compareTo(pass1) == 0)
                {
                    Contact contact = new Contact();
                    contact.name = nameUserStr;
                    contact.login = loginStr;
                    contact.password = pass0;

                    dialogWaiting.show();
                    model.regRegistrationListener(new RegistrationListener() {

                        @Override
                        public void handlerEvent(int typeResponse) {
                                Message message = new Message();
                                message.arg1 = typeResponse;
                                mHandler.sendMessage(message);
                        }


                    });
                    model.registration(contact);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                    builder.setTitle("Ошибка ввода данных");
                    builder.setMessage("Пароль и подтверждение пароля не совпадают. Повторите ввод пароля");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }

            }
            else
            {
                String msg = "";
                if(loginStr.length() == 0)
                    msg = "Вы забыли ввести логин";
                else if(nameUserStr.length() == 0)
                    msg = "Вы забыли ввести имя";
                else if(pass0.length() == 0)
                    msg = "Вы забыли ввести пароль";
                else if(pass1.length() == 0)
                    msg = "Введите подтверждение пароля";

                AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                builder.setTitle("Ошибка ввода данных");
                builder.setMessage(msg);
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
        setContentView(R.layout.activity_reg);

        loginField = (TextView) findViewById(R.id.login_editText);
        nameUser = (TextView) findViewById(R.id.name_editText);
        passwordField = (TextView) findViewById(R.id.password0_editText);
        passwordField2 = (TextView) findViewById(R.id.password2_editText);

        regButton = (Button) findViewById(R.id.button);
        regButton.setOnClickListener(regButtonListener);

        model = new Model();


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegActivity.this);
        dialogBuilder.setMessage("Ожидание ответа от сервера");
        dialogBuilder.setTitle("Проверка данных");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(R.layout.dialog_waitig);

        dialogBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                model.regRegistrationListener(new RegistrationListener() {
                    @Override
                    public void handlerEvent(int typeResponse) {

                    }

                });
            }
        });

        dialogWaiting = dialogBuilder.create();


    }
}
