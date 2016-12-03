/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
package com.sa.proxapp.com.sa.ClientClass;


public interface SubSystemMSGInterface {
    void addContact(Contact contact, ReportListener reportListener);//+
    void delContact(Contact contact, ReportListener reportListener);//+
    void requestListContacts(ReportListener reportListener); //добавил 29.11 //+
    void loginMe(String login,String password, ReportListener reportListener);//+
    void registration(Contact contact, ReportListener reportListener);//+
    void findContact(Contact contact, ReportListener reportListener);//+

    void registrListener(ReportListener reportListener);
    void sendMessage(Message message, ReportListener reportListener);
}
