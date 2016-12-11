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
    private UniversalListener sendingCallBack;

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
                if(TESTSTAT == false)
                    subSystemMSG.addContact(contact,reportListener);
                else {
                    Contact contact1 = new Contact();
                    contact1.login = "123";
                    contact1.name = "Name123";
                    contact1.status = 1;
                    addContactListener.handlerEvent(contact1);
                }
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
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    Contact contact;
                    for (int i = 0; i < 20; ++i) {
                        contact = new Contact();
                        contact.name = Integer.toString(i);
                        contact.login = "L" + contact.name;
                        contact.status = i % 2;
                        contact.countOfMes = contact.status * i*10;
                        contactArrayList.add(contact);
                    }
                    getListContactListener.handleEvent(contactArrayList);
                }

            }
        });
        myThready.start();	//Запуск потока
    }

    //add 06.12
    //для сокращения повтора кода getListDialog и getUpdateDialog
    private ReportListener getReportListenerForListDialog(final GetListDialogListener listener)
    {
       final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.SUCCESSFUL_MES){
                    ArrayList<Message> messages = new ArrayList<>();
                    String strListArr = (String) report.data;
                    try {
                        JSONObject jsonObj;
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(strListArr);
                        jsonObj = (JSONObject) obj;

                        JSONArray arr = (JSONArray) jsonObj.get("messages");// new JSONArray();
                        Iterator iter = arr.iterator();
                        String cont;
                        Message msg;
                        while (iter.hasNext()) {
                            cont = (String) iter.next();
                            msg = (Message) JSONCoder.decode(cont, Report.MESSAGE);
                            messages.add(msg);
                        }

                        // System.out.println(arr.toString());
                        listener.handlerEvent(messages);
                    }
                    catch (Exception e) {
                        System.out.println("public void getListContact()" + e.toString());
                    }
                }
                else
                    listener.handlerEvent(null);
            }
        };
        return reportListener;
    }

    @Override
    public void getListDialog(final Contact contact) {
        final ReportListener reportListener = getReportListenerForListDialog(getListDialogListener);
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestDialog(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getUpdateDialog(final Contact contact, final GetListDialogListener listener) {
        final ReportListener reportListener = getReportListenerForListDialog(listener);
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                if(TESTSTAT == false)
                    subSystemMSG.requestUpdateDialog(contact,reportListener);
                else {
                    ArrayList<Message> messages = new ArrayList<>();
                    Message message = new Message();
                    message.text = "TEST";
                    message.contact = null;
                    message.date = "fff";
                    message.time = "ffffff";
                    messages.add(message);
                    listener.handlerEvent(messages);
                }
            }
        });
        myThready.start();	//Запуск потока
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
                if(TESTSTAT == false) {
                    subSystemMSG.loginMe(login, password, reportListener);
                    //subSystemMSG.loginMe("Tony", "123", reportListener);
                }
                else {
                    //заглушка
                    //При изменении не забыть убрать предупреждение о заглушке наверху

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

    //add 06.12
    @Override
    public void sendMessage(final Message message) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.SUCCESSFUL_SEND_MES){
                    sendingCallBack.handlerEvent(Report.SUCCESSFUL_SEND_MES);
                }
                else
                    sendingCallBack.handlerEvent(0);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.sendMessage(message,reportListener);
            }
        });
        myThready.start();	//Запуск потока
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

    @Override
    public void regSendingCallBack(UniversalListener listener) {
        sendingCallBack = listener;
    }

    @Override
    public void setMyStatus(final int status, final UniversalListener listener) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                listener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.sendStatus(status,reportListener); // поменять константы
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getMyContact(final UniversalListenerWithObject listener) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                listener.handlerEvent(report.type,report.data);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestMyContact(reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getUpdateContacts(final GetListContactListener listener) {
        final ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.NO_UPDATES)
                    listener.handleEvent(null);
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
                        while (iter.hasNext()) {
                            cont = (String) iter.next();
                            contact = (Contact) JSONCoder.decode(cont, 2);
                            contactArrayList.add(contact);
                        }
                        //System.out.println(arr.toString());
                    } catch (Exception e) {
                        System.out.println("public void getListContact()" + e.toString());
                    }
                    ;
                    listener.handleEvent(contactArrayList);
                }
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestUpdateContacts(reportListener);
            }
        });
        myThready.start();	//Запуск потока
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