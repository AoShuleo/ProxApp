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
import com.sa.proxapp.com.sa.ClientClass.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 07.12.2016 in ProxApp project
 * .
 */

public class DialogAdapter extends ArrayAdapter<Message> {

    ArrayList<Message> messagesArrayList;
    Context context;
    int idRes;
    private Activity activity;

    Contact toContact;


    public DialogAdapter(Context context, int resource, ArrayList<Message> objects) {
        super(context, resource, objects);

        messagesArrayList = objects;
        this.context = context;
        idRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messagesArrayList.get(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(idRes, null);
        }

        TextView fromUser = (TextView) convertView.findViewById(R.id.from_user_textview);
        TextView messagaTextView = (TextView) convertView.findViewById(R.id.message_textview);
        TextView dateTime = (TextView) convertView.findViewById(R.id.date_time_textview);

        if(message.contact != null)
            fromUser.setText(message.contact.login + " написал:");
        else
            fromUser.setText(toContact.login + " написал:");
        messagaTextView.setText(message.text);
        dateTime.setText(message.time + " " + message.date);

        return convertView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
