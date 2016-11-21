/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
package com.sa.proxapp.com.sa.ClientClass;


public interface SubSystemMSGInterface {
    void addContact(Contact contact, ReportListener reportListener);
    void auth(Contact contact, ReportListener reportListener, String string);
    void delContact(Contact contact, ReportListener reportListener);
    void findContact(Contact contact, ReportListener reportListener, int I1, int I2);
    void registration(Contact contact, ReportListener reportListener);
    void registrListener(ReportListener reportListener);
    void sendMessage(Message message, ReportListener reportListener);
    void loginMe(String login,String password, ReportListener reportListener);
}
