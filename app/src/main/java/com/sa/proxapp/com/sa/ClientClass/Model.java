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
//Внимание, заглушка на успешное получение списка контактов в методе getListContact
//Стоит заглушка на успешное удаление контакта
//Внимание, заглушка на успешный поиск контактов

import java.util.ArrayList;

public class Model implements ModelOnClientInterface {

    final boolean TESTSTAT = true;

    RegistrationListener registrationListener;
    LoginMeListener loginMeListener;
    GetListDialogListener getListDialogListener;
    GetListContactListener getListContactListener;
    AddContactListener addContactListener;
    SubSystemMSG subSystemMSG;

    private UniversalListener delContactListener;
    private GetListContactListener findContactsListener;


    public Model (){
        subSystemMSG = new SubSystemMSG();
    }

    @Override
    public void addContact(final Contact contact) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.SUCCESSFUL_ADD){ //если нашел контакт
                    Contact contact = (Contact) JSONCoder.decode((String) report.data,2);
                    addContactListener.handlerEvent(contact);
                }
                else
                    addContactListener.handlerEvent(null);
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

                if(TESTSTAT == false)
                    subSystemMSG.requestListContacts(reportListener);
                else {

                    //Внимание, заглушка на успешное получение списка контактов
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    Contact contact;
                    for (int i = 0; i < 20; ++i) {
                        contact = new Contact();
                        contact.name = Integer.toString(i);
                        contact.login = "L" + contact.name;
                        contactArrayList.add(contact);
                    }
                    getListContactListener.handleEvent(contactArrayList);
                }

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
                /*if(TESTSTAT == false) {
                    subSystemMSG.loginMe(login, password, reportListener);
                }
                else*/ {
                    //заглушка
                    //При изменении не забыть убрать предупреждение о заглушке наверху
                    //subSystemMSG.loginMe("Tony", "123", reportListener);
                    loginMeListener.handlerEvent(Report.SUCCESSFUL_AUTH);
                }

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

    @Override
    public void regFindContactsListener(GetListContactListener listener) {
        findContactsListener = listener;
    }

    //add 02.12
    @Override
    public void deleteContact(final Contact contact) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                delContactListener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                if(TESTSTAT == false)
                    subSystemMSG.delContact(contact,reportListener);
                else {
                    //заглушка
                    //При изменении не забыть убрать предупреждение о заглушке наверху
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    delContactListener.handlerEvent(Report.SUCCESSFUL_DEL);
                }
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void findContacts(final Contact contact) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if(report.data != null)
                {
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    String strListArr = (String) report.data;
                    try {
                        JSONObject jsonObj;
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(strListArr);
                        jsonObj = (JSONObject) obj;

                        JSONArray arr = (JSONArray) jsonObj.get("findList");// new JSONArray();
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
                    findContactsListener.handleEvent(contactArrayList);
                }
                else
                    findContactsListener.handleEvent(null);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                if(TESTSTAT == false)
                    subSystemMSG.findContact(contact, reportListener);
                else
                {
                    //Внимание, заглушка на успешное получение списка контактов
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    Contact contact;
                    for(int i = 0; i < 20; ++i)
                    {
                        contact = new Contact();
                        contact.name =  Integer.toString(i);
                        contact.login = "F"+contact.name;
                        contactArrayList.add(contact);
                    }
                    findContactsListener.handleEvent(contactArrayList);
                }
            }
        });
        myThready.start();	//Запуск потока
    }

    //add 02.12
    @Override
    public void regDelContactListener(UniversalListener delContactListener) {
        this.delContactListener = delContactListener;
    }
}