/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
package com.sa.proxapp.com.sa.ClientClass;
public interface ModelOnClientInterface {
    void addContact(Contact contact);

    void getListContact(); //done
    void deleteContact(Contact contact);//done
    void findContacts(Contact contact);//done

    void getListDialog(Contact contact);//done
    void getUpdateDialog(Contact contact, GetListDialogListener listener);

    void loginMe(String login,String password);//done
    void registration(Contact contact);//done

    void sendMessage(Message message);//done

    void regGetListContactListener(GetListContactListener listener);//done
    void regGetListDialogListener(GetListDialogListener listener);//done
    void regRegistrationListener(RegistrationListener listener);//done
    void regAddContactListener(AddContactListener listener);//done
    void regDelContactListener(UniversalListener delContactListener);//done
    void regLoginMeListener(LoginMeListener listener);//done
    void regFindContactsListener(GetListContactListener listener);//done
    void regSendingCallBack(UniversalListener listener);//done
}
