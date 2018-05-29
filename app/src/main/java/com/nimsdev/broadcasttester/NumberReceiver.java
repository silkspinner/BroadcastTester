package com.nimsdev.broadcasttester;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

public class NumberReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.saveNumber(number, database);
            dbHelper.close();
        }

        Intent intent1 = new Intent(DbContract.UPDATE_UI_FILTER);
        context.sendBroadcast(intent);
    }
}
