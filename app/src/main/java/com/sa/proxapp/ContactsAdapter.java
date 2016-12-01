package com.sa.proxapp;


import android.content.Context;
import android.support.annotation.NonNull;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 21.11.2016 in ProxApp project
 * .
 */

public class ContactsAdapter extends ArrayAdapter<Contact>{

    ArrayList<Contact> contactArrayList;
    Context context;
    int idRes;



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
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.extra_menu, popupMenu.getMenu());

                    //обработка нажатий пунктов меню
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Contact chCont = contactArrayList.get(position);
                            switch (item.getItemId()) {
                                case R.id.item_extra_menu0:
                                    Toast.makeText(getContext(), "Доп. контакта " + chCont.login, Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.item_extra_menu1:
                                    Toast.makeText(getContext(), "Удаление контакта " + chCont.login, Toast.LENGTH_SHORT).show();
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
}
