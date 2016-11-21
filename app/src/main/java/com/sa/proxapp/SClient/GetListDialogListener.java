package com.sa.proxapp.SClient;

import com.sa.proxapp.com.sa.ClientClass.Message;

import java.util.ArrayList;

/**
 * Created by Android on 17.10.2016 in ProxApp project
 * .
 */

public interface GetListDialogListener {
    void handleEvent(ArrayList<Message> messageArrayList);
}
