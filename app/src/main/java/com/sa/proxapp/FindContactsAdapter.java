package com.sa.proxapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sa.proxapp.com.sa.ClientClass.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 05.12.2016 in ProxApp project
 * .
 */

public class FindContactsAdapter extends ArrayAdapter<Contact> {
    ArrayList<Contact> contactArrayList;
    Context context;
    int idRes;
    private Activity activity;

    public FindContactsAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
        contactArrayList = objects;
        this.context = context;
        idRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact cont = contactArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(idRes, null);
        }
        TextView lab1 = (TextView) convertView.findViewById(R.id.label_el);
        TextView lab2 = (TextView) convertView.findViewById(R.id.label_el2);
        lab1.setText(cont.login);
        lab2.setText(cont.name);

        return convertView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
