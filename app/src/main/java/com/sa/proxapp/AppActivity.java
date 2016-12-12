package com.sa.proxapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.Message;
import com.sa.proxapp.com.sa.ClientClass.Model;
import com.sa.proxapp.com.sa.ClientClass.UniversalListener;
import com.sa.proxapp.com.sa.ClientClass.UniversalListenerWithObject;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity
{

    FragmentManager fragmentManager;
    FloatingActionButton fab;
    ListContactsFragment listContactsFragment;
    ListFindContactsFragment findContactsFragment;
    DialogFragment dialogFragment;

    Model model = new Model();
    boolean firstStart = true;
    Activity activity = this;

    ListContactsFragment.OnClickContact onClickContact = new ListContactsFragment.OnClickContact() {
        @Override
        public void handlerClick(Contact contact) {
            fab.hide();
            dialogFragment.curContact = contact;

            setTitle("Диалог с " + contact.login);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, dialogFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    };


    android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.arg1)
            {
                case 20:
                    Contact contact = (Contact)msg.obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.info_contact_dialog,null);
                    TextView iLogin = (TextView)view.findViewById(R.id.info_login);
                    TextView iName = (TextView)view.findViewById(R.id.info_name);

                    iLogin.setText(contact.login);
                    iName.setText(contact.name);

                    builder.setView(view);
                    builder.setTitle("Информация о себе");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        setTitle("Список контактов");

        fragmentManager = getFragmentManager();

        findContactsFragment = new ListFindContactsFragment();
        listContactsFragment = new ListContactsFragment();
        dialogFragment = new DialogFragment();

        listContactsFragment.clickContact = onClickContact;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, listContactsFragment);
        transaction.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setTitle("Поиск контакта");
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, findContactsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fab.hide();
            }
        });
/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @Override
    public void onBackPressed() {
       /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        super.onBackPressed();
        fab.show();
        setTitle("Список контактов");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_my_contact) {
            model.getMyContact(new UniversalListenerWithObject() {
                @Override
                public void handlerEvent(int typeResponse, Object object) {
                    if(object != null)
                    {
                        android.os.Message message = new android.os.Message();
                        message.obj = object;
                        message.arg1 = 20;
                        mHandler.sendMessage(message);
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        model.setMyStatus(0, new UniversalListener() {
            @Override
            public void handlerEvent(int typeResponse) {

            }
        });
        firstStart = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!firstStart) {
            model.setMyStatus(1, new UniversalListener() {
                @Override
                public void handlerEvent(int typeResponse) {
                    System.out.println(typeResponse);
                }
            });
        }
    }
}
