/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
package com.sa.proxapp.com.sa.ClientClass;


import com.sa.proxapp.com.sa.ClientClass.simple.*;
import com.sa.proxapp.com.sa.ClientClass.simple.parser.JSONParser;


import java.util.ArrayList;
import java.util.Iterator;

//Внимание!!!
//Стоит заглушка на успешную аутентификацию в методе loginMe


import java.util.ArrayList;

public class Model implements ModelOnClientInterface {

    RegistrationListener registrationListener;
    LoginMeListener loginMeListener;
    GetListDialogListener getListDialogListener;
    GetListContactListener getListContactListener;
    AddContactListener addContactListener;
    SubSystemMSG subSystemMSG;
    public Model (){
        subSystemMSG = new SubSystemMSG();
    }

    @Override
    public void addContact(final Contact contact) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.NOT_FIND_CONTACT){ //если не нашел контакт
                    addContactListener.handlerEvent(null);
                }
                if (report.type == Report.FIND_CONTACT){ //если нашел контакт
                    Contact contact = (Contact) report.data;
                    addContactListener.handlerEvent(contact);
                }
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.addContact(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getListContact() {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if(report.type != Report.SUCCESSFUL_FRIENDS)
                    getListContactListener.handleEvent(null);
                else {
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    String strListArr = (String) report.data;
                    try {
                        JSONObject jsonObj;
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(strListArr);
                        jsonObj = (JSONObject) obj;

                        JSONArray arr = (JSONArray) jsonObj.get("friends");// new JSONArray();
                        Iterator iter = arr.iterator();
                        String cont;
                        Contact contact;
                        while(iter.hasNext())
                        {
                            cont = (String) iter.next();
                            contact = (Contact)JSONCoder.decode(cont, 2);
                            contactArrayList.add(contact);
                        }

                        System.out.println(arr.toString());

                    }
                    catch (Exception e) {
                        System.out.println("public void getListContact()" + e.toString());
                    };
                    getListContactListener.handleEvent(contactArrayList);
                }
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestListContacts(reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getListDialog(Contact contact) {

    }

    @Override
    public void loginMe(final String login,final String password) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {

                loginMeListener.handlerEvent(report.type);

            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                //subSystemMSG.loginMe(login,password,reportListener);
                //заглушка
                //При изменении не забыть убрать предупреждение
                subSystemMSG.loginMe("Tony", "123", reportListener);
               // loginMeListener.handlerEvent(Report.SUCCESSFUL_AUTH);

            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void registration(final Contact contact) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                registrationListener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.registration(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void regGetListContactListener(GetListContactListener listener) {
        getListContactListener = listener;
    }

    @Override
    public void regGetListDialogListener(GetListDialogListener listener) {
        getListDialogListener = listener;
    }

    @Override
    public void regAddContactListener(AddContactListener listener) {
        addContactListener = listener;
    }

    @Override
    public void regRegistrationListener(RegistrationListener listener) {
        registrationListener = listener;
    }

    @Override
    public void regLoginMeListener(LoginMeListener listener) {loginMeListener = listener; }
}