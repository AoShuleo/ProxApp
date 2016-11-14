package com.sa.proxapp.com.sa.ClientClass;


/**
 * Created by Android on 17.10.2016 in ProxApp project
 * .
 */

public interface ModelOnClientInterface
{
    void findContact(Contact contact);
    void getListContact();
    void getListDialog(Contact contact);
    void loginMe(String login,String password);
    void registration(Contact contact);
    void sendMessage(Message message);



    void regGetListContactListener(GetListContactListener listener);
    void regGetListDialogListener(GetListDialogListener listener);
    void regRegistrationListener(RegistrationListener listener);
    void regLoginMeListener(LoginListener listener);
}
