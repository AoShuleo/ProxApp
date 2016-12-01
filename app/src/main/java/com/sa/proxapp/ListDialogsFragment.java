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

import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.GetListContactListener;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.Report;

import java.util.ArrayList;

/**
 * Created by Android on 14.11.2016 in ProxApp project
 * .
 */

public class ListDialogsFragment extends ListFragment  {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_list_dialog,null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new ArrayList<>();
        adapter = new ContactsAdapter(getActivity(),R.layout.list__element_contact,contacts);
        ((ContactsAdapter) adapter).setActivity(getActivity());
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onStart() { //вызывается каждый раз при появлении фрагмента (активности?)
        super.onStart();



        model.regGetListContactListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                Message message = new Message();
                message.obj = contactArrayList;
                mHandler.sendMessage(message);
            }
        });
        //System.out.println("List start////////////////////");
        model.getListContact();
    }

}
