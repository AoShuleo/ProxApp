package com.sa.proxapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.proxapp.com.sa.ClientClass.AddContactListener;
import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.GetListContactListener;
import com.sa.proxapp.com.sa.ClientClass.Model;

import java.util.ArrayList;

/**
 * Created by Android on 05.12.2016 in ProxApp project
 * .
 */

public class ListFindContactsFragment extends Fragment {


    Model model;
    ArrayList<Contact> contacts;
    ListAdapter adapter;

    View view;
    Button findButton;
    TextView findLoginTextView;
    TextView findNameTextView;
    ListView listView;

    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.arg1)
            {
                case 0:
                    ArrayList<Contact> con = (ArrayList) msg.obj;
                    if(con.size() > 0) {
                        contacts.clear();
                        if (msg.obj instanceof ArrayList) {
                            for (Contact el : con) {
                                contacts.add(el);
                            }
                            listView.setAdapter(adapter);
                        } else {
                            System.out.println("public void handleMessage(android.os.Message msg) cast problems");
                            throw new NullPointerException();
                        }
                    }
                    else
                        Toast.makeText(getActivity(), "Контакт не найден", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Contact c = (Contact) msg.obj;
                    Toast.makeText(getActivity(), "Добавлен " + c.login, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_contacts,container,false);

        findButton = (Button) view.findViewById(R.id.buttonFind);
        findLoginTextView = (TextView) view.findViewById(R.id.editText4);
        findNameTextView = (TextView) view.findViewById(R.id.editText5);
        listView = (ListView) view.findViewById(R.id.list_contacts);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Contact chCont = contacts.get(position);
                //Toast.makeText(getActivity(), "Нажат" + contact.login, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View viewInfo = inflater.inflate(R.layout.info_contact_dialog,null);
                TextView iLogin = (TextView)viewInfo.findViewById(R.id.info_login);
                TextView iName = (TextView)viewInfo.findViewById(R.id.info_name);

                iLogin.setText(chCont.login);
                iName.setText(chCont.name);

                builder.setView(viewInfo);
                builder.setTitle("Информация о контакте");
                builder.setPositiveButton("Добавть в свой список", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.addContact(chCont);
                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
                    IBinder iBinder = getActivity().getCurrentFocus().getWindowToken();
                    imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                catch (Exception e)
                {

                }

                Contact contact = new Contact();
                contact.login = findLoginTextView.getText().toString();
                contact.name = findNameTextView.getText().toString();
                if(contact.login.length() > 0 || contact.name.length() > 0)
                    model.findContacts(contact);
            }
        });



        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        model = new Model();
        contacts = new ArrayList<>();
        adapter = new FindContactsAdapter(getActivity(),R.layout.list_element_find_contact,contacts);
        ((FindContactsAdapter) adapter).setActivity(getActivity());


        model.regFindContactsListener(new GetListContactListener() {
            @Override
            public void handleEvent(ArrayList<Contact> contactArrayList) {
                Message message = new Message();
                message.arg1 = 0;
                message.obj = contactArrayList;
                mHandler.sendMessage(message);
            }
        });

        model.regAddContactListener(new AddContactListener() {
            @Override
            public void handlerEvent(Contact contact) {
                Message message = new Message();
                message.arg1 = 1;
                message.obj = contact;
                if(contact != null)
                    mHandler.sendMessage(message);
            }
        });
    }


}
