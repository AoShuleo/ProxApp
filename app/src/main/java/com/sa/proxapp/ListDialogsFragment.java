package com.sa.proxapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class ListDialogsFragment extends ListFragment  {

    Model model = new Model();
    ArrayList<Contact> contacts;
    ListAdapter adapter;
    private Contact contactForDelete;

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
      return inflater.inflate(R.layout.fragment_list_dialog,null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new Model();
        contacts = new ArrayList<>();
        adapter = new ContactsAdapter(getActivity(),R.layout.list__element_contact,contacts);

        ((ContactsAdapter) adapter).setActivity(getActivity());
        ((ContactsAdapter) adapter).setDelContact(new UniversalListenerWithObject() {
            @Override
            public void handlerEvent(int typeResponse, Object object) {
                contactForDelete = (Contact) object;
                model.deleteContact(contactForDelete);
            }
        });

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



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() { //вызывается каждый раз при появлении фрагмента (активности?)
        super.onStart();

        //System.out.println("List start////////////////////");
        model.getListContact();
    }

}
