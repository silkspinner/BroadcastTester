package com.nimsdev.broadcasttester;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<IncomingNumber> arrayList = new ArrayList<>();
    private RecyclerAdapter adapter;

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        textView = (TextView) findViewById(R.id.statusTextView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        readFromDb();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readFromDb();
            }
        };
    }

    private void readFromDb() {
        arrayList.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = dbHelper.readNumber(database);

        if(cursor.moveToNext()) {
            while(cursor.moveToNext()) {
                String number;
                int id;
                number = cursor.getString(cursor.getColumnIndex(DbContract.INCOMING_NUMBER));
                id = cursor.getInt(cursor.getColumnIndex("id"));
                arrayList.add(new IncomingNumber(id, number));

            }
            cursor.close();
            dbHelper.close();

            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(DbContract.UPDATE_UI_FILTER));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
