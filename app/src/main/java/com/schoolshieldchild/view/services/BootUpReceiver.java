package com.schoolshieldchild.view.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {

                System.out.println("REBOOTED APPLICATION");
            }

}