package com.sa.proxapp;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.GetListContactListener;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.Report;
import com.sa.proxapp.com.sa.ClientClass.UniversalListener;
import com.sa.proxapp.com.sa.ClientClass.UniversalListenerWithObject;

import java.util.ArrayList;

/**
 * Created by Android on 14.11.2016 in ProxApp project
 * .
 */

public class ListContactsFragment extends ListFragment  {
    Model model;
    ArrayList<Contact> contacts;
    ListAdapter adapter;
    private Contact contactForDelete;

    AlertDialog dialogWaiting;

    interface OnClickContact
    {
        void handlerClick(Contact contact);
    }
    OnClickContact clickContact;

    public ListContactsFragment() {
        super();
        model = new Model();


        model.regGetListContactListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                Message message = new Message();
                message.arg1 = 0;
                message.obj = contactArrayList;
                mHandler.sendMessage(message);
            }
        });

        model.regDelContactListener(new UniversalListener() {
            @Override
            public void handlerEvent(int typeResponse) {
                Message message = new Message();
                message.arg1 = 1;
                message.arg2 = typeResponse;
                mHandler.sendMessage(message);
            }
        });


    }

    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
        //arg1//0 - получение списока
             //1 - удаление контакта
            switch (msg.arg1) {
                case 0:
                    contacts.clear();
                    if (msg.obj instanceof ArrayList)
                    {
                        ArrayList<Contact> con = (ArrayList) msg.obj;
                        for (Contact el : con) {
                            contacts.add(el);
                        }
                        setListAdapter(adapter);
                    }
                    else {
                        System.out.println("public void handleMessage(android.os.Message msg) cast problems");
                        throw new NullPointerException();
                    }
                    break;
                case 1:
                    dialogWaiting.hide();
                    if(msg.arg2 == Report.SUCCESSFUL_DEL)
                    {
                        Toast.makeText(getActivity(),"Контакт удален", Toast.LENGTH_SHORT).show();
                        contacts.remove(contactForDelete);
                        setListAdapter(adapter);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Контакт не удален", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dialog,null);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new ArrayList<>();
        adapter = new ContactsAdapter(getActivity(),R.layout.list_element_contact,contacts);

        ((ContactsAdapter) adapter).setActivity(getActivity());
        ((ContactsAdapter) adapter).setDelContact(new UniversalListenerWithObject() {
            @Override
            public void handlerEvent(int typeResponse, Object object) {
                contactForDelete = (Contact) object;
                dialogWaiting.show();
                model.deleteContact(contactForDelete);
            }
        });

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("Ожидание ответа от сервера");
        dialogBuilder.setTitle("Подождите немного, пожалуйста");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(R.layout.dialog_waitig);

        dialogBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        dialogWaiting = dialogBuilder.create();
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() { //вызывается каждый раз при появлении фрагмента (активности?)
        super.onStart();
        model.getListContact();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Toast.makeText(getActivity(),"Нажат элемент листа", Toast.LENGTH_SHORT).show();
        if(clickContact != null)
            clickContact.handlerClick(contacts.get(position));
    }
}
