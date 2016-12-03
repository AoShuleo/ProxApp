package com.sa.proxapp;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.Report;
import com.sa.proxapp.com.sa.ClientClass.UniversalListenerWithObject;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Android on 21.11.2016 in ProxApp project
 * .
 */

public class ContactsAdapter extends ArrayAdapter<Contact>{

    ArrayList<Contact> contactArrayList;
    Context context;
    int idRes;
    private Activity activity;

    /**
     * внешний листенер для обработки удаления контакта в активности
     */
    private UniversalListenerWithObject delContact;

    public ContactsAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
        contactArrayList = objects;
        this.context = context;
        idRes = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       // if(position < contactArrayList.size()) {
            Contact cont = contactArrayList.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(idRes, null);
            }
            TextView lab1 = (TextView) convertView.findViewById(R.id.label_el);
            TextView lab2 = (TextView) convertView.findViewById(R.id.label_el2);
            lab1.setText(cont.login);
            lab2.setText(cont.name);

            //if(position + 1 == contactArrayList.size())
            //    convertView.

            Button but = (Button) convertView.findViewById(R.id.extar_button_on_contact);

            //обработка нажатия доп. кнопки
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //формирование экстра меню
                    PopupMenu popupMenu = new PopupMenu(getContext(), v);
                    final MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.extra_menu, popupMenu.getMenu());

                    //обработка нажатий пунктов меню
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            final Contact chCont = contactArrayList.get(position);
                            switch (item.getItemId()) {
                                case R.id.item_extra_menu0: //пункт меню - доп. информация
                                    //Toast.makeText(getContext(), "Доп. контакта " + chCont.login, Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                    LayoutInflater inflater = activity.getLayoutInflater();
                                    View view = inflater.inflate(R.layout.info_contact_dialog,null);
                                    TextView iLogin = (TextView)view.findViewById(R.id.info_login);
                                    TextView iName = (TextView)view.findViewById(R.id.info_name);

                                    iLogin.setText(chCont.login);
                                    iName.setText(chCont.name);

                                    builder.setView(view);
                                    builder.setTitle("Информация о контакте");
                                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                    return true;
                                case R.id.item_extra_menu1: //пункт меню - удаление контакта
                                    AlertDialog.Builder builderDel = new AlertDialog.Builder(getContext());
                                    builderDel.setTitle("Подтвердите удаление контакта");
                                    builderDel.setMessage("Контакт " + chCont.login + " будет удален из вашего списка.\n" +
                                            "Выполнить это действие?");
                                    builderDel.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delContact.handlerEvent(Report.DEL_FRIEND,chCont);
                                        }
                                    });
                                    builderDel.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {}
                                    });
                                    builderDel.setCancelable(true);
                                    builderDel.create().show();
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
     //   }
        return convertView;

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setDelContact(UniversalListenerWithObject delContact) {
        this.delContact = delContact;
    }
}
