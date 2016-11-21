package com.sa.proxapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.sa.proxapp.com.sa.ClientClass.Contact;

import java.util.ArrayList;

/**
 * Created by Android on 14.11.2016 in ProxApp project
 * .
 */

public class ListDialogsFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ArrayList<Contact> contacts;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        contacts = new ArrayList<>();
        Contact testContact = new Contact();
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
        contacts.add(testContact);

        ListAdapter adapter = new ContactsAdapter(getActivity(),
                R.layout.list__element_contact,contacts);
        setListAdapter(adapter);

    }
    
}
