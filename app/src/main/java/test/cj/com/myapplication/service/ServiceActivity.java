package test.cj.com.myapplication.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import test.cj.com.myapplication.R;

/**
 * Created by Administrator on 2019/5/15.
 */

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "ServiceActivity";

    private MyService.MyBinder myBinder;
    private ServiceConnection connection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(ServiceActivity.this, MyService.class);
                startService(startIntent);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(ServiceActivity.this, MyService.class);
                stopService(stopIntent);
            }
        });
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MyService.MyBinder) service;
                Log.e(TAG, myBinder.getString());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myBinder = null;
            }
        };
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(ServiceActivity.this, MyService.class);
                bindService(bindIntent, connection, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

    }
}
