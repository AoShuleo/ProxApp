package com.sa.proxapp.com.sa.ClientClass;

import java.util.ArrayList;

/**
 * Created by Android on 17.10.2016 in ProxApp project
 * .
 */

public class Model implements ModelOnClientInterface
{
    RegistrationListener registrationListener;
    LoginListener loginListener;

    @Override
    public void findContact(Contact contact) {

    }

    @Override
    public void getListContact() {

    }

    @Override
    public void getListDialog(Contact contact) {

    }

    @Override
    public void loginMe(String login, String password) {
        Runnable inter = new Runnable() {
            @Override
            public void run() {
                try {
                    double d = 234235.23423;
                    for(int i = 0; i < 1000000000; ++i)
                        d *= d;

                }
                catch (Exception ex) {

                }
                loginListener.handleEvent(0);
            }
        };
        Thread thread = new Thread(inter);
        thread.start();
    }

    @Override
    public void registration(Contact contact) {
        Runnable inter = new Runnable() {
            @Override
            public void run() {
                try {
                    double d = 234235.23423;
                    for(int i = 0; i < 1000000000; ++i)
                        d *= d;

                }
                catch (Exception ex) {

                }
                registrationListener.handleEvent(0);
            }
        };
        Thread thread = new Thread(inter);
        thread.start();
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void regGetListContactListener(GetListContactListener listener) {

    }

    @Override
    public void regGetListDialogListener(GetListDialogListener listener) {

    }

    @Override
    public void regRegistrationListener(RegistrationListener listener) {

        registrationListener = listener;
    }

    @Override
    public void regLoginMeListener(LoginListener listener) {
        loginListener = listener;
    }
}
