package com.sa.proxapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.sa.proxapp.com.sa.ClientClass.Contact;
import com.sa.proxapp.com.sa.ClientClass.Model;

public class AppActivity extends AppCompatActivity
{

    FragmentManager fragmentManager;
    FloatingActionButton fab;
    ListContactsFragment listContactsFragment;
    ListFindContactsFragment findContactsFragment;
    DialogFragment dialogFragment;

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
        if (id == R.id.action_settings) {
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
}
