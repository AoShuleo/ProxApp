package com.sa.proxapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.GetListDialogListener;
import com.sa.proxapp.com.sa.ClientClass.Message;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.UniversalListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DialogFragment extends Fragment {

    Model model;

    ArrayList<Message> messageArrayList;
    ArrayAdapter<Message> adapter;

    View view;
    Contact curContact;
    Button sendButton;
    TextView messageTextView;
    ListView dialogListView;

    Message sendingMessage;

    View.OnClickListener buttonSendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strMes = messageTextView.getText().toString();
            if(strMes.length() > 0) {
                Message message = new Message();
                message.text = strMes;
                Contact contact = new Contact();
                contact.login = curContact.login;
                message.contact = contact;

                Date date = new Date(System.currentTimeMillis());
                message.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
                message.time = new SimpleDateFormat("HH:mm:ss").format(date);

                sendingMessage = message;
                model.sendMessage(message);
            }
        }
    };


    public DialogFragment() {
        // Required empty public constructor

        model = new Model();

        model.regSendingCallBack(new UniversalListener() {
            @Override
            public void handlerEvent(int typeResponse) {
                android.os.Message message = new android.os.Message();
                message.arg1 = 0;
                message.arg2 = typeResponse;
                mHandler.sendMessage(message);
            }
        });

        model.regGetListDialogListener(new GetListDialogListener() {
            @Override
            public void handlerEvent(ArrayList<Message> messageArrayList) {
                android.os.Message message = new android.os.Message();
                message.arg1 = 1;
                message.obj = messageArrayList;
                mHandler.sendMessage(message);
            }
        });
    }

    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.arg1)
            {
                //отправка сообщений
                case 0:
                    if(msg.arg2  == 0)
                        Toast.makeText(getActivity(),"Сообщение не отправлено", Toast.LENGTH_SHORT).show();
                    else {

                        messageTextView.setText("");
                        sendingMessage.contact.login = "I am";
                        messageArrayList.add(sendingMessage);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 1:
                    messageArrayList.clear();
                    ArrayList<Message> mes = (ArrayList) msg.obj;
                    for (Message el : mes) {
                        messageArrayList.add(el);
                    }
                    dialogListView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog, container, false);
        sendButton = (Button) view.findViewById(R.id.button2);
        messageTextView = (TextView) view.findViewById(R.id.editText3);
        dialogListView = (ListView) view.findViewById(R.id.list_dialog);

        messageTextView.setText("");
        sendButton.setOnClickListener(buttonSendListener);
        dialogListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageArrayList = new ArrayList<>();
        adapter = new DialogAdapter(getActivity(),R.layout.list_element_message,messageArrayList);
        ((DialogAdapter)adapter).setActivity(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        model.getListDialog(curContact);
    }
}
