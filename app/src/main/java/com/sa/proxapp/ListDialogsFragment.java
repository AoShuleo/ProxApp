package com.sa.proxapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.GetListContactListener;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.Report;

import java.util.ArrayList;

/**
 * Created by Android on 14.11.2016 in ProxApp project
 * .
 */

public class ListDialogsFragment extends ListFragment {

    Model model = new Model();
    ArrayList<Contact> contacts;
    ListAdapter adapter;

    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            //super.handleMessage(msg);
            contacts.clear();

            ArrayList<Contact> con = (ArrayList<Contact>)msg.obj;
            for (Contact el: con) {
                contacts.add(el);
            }

            setListAdapter(adapter);

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onStart() {
        super.onStart();

        contacts = new ArrayList<>();
        adapter = new ContactsAdapter(getActivity(),
                R.layout.list__element_contact,contacts);

        model.regGetListContactListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                Message message = new Message();
                message.obj = contactArrayList;
                mHandler.sendMessage(message);
            }
        });
        System.out.println("List start////////////////////");
        model.getListContact();

        /*Contact testContact = new Contact();
        testContact.login = "1Login";
        testContact.name = "1Name";
        contacts.add(testContact);
        testContact = new Contact();
        testContact.login = "2Login";
        testContact.name = "2Name";
        contacts.add(testContact);
        testContact = new Contact();
        testContact.login = "3Login";
        testContact.name = "3Name";
        contacts.add(testContact);*/



    }

}
