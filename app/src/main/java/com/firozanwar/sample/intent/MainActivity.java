package com.firozanwar.sample.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartService, btnStartActivity, btnSendBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartActivity = (Button) findViewById(R.id.btn_start_activity);
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnSendBroadcast = (Button) findViewById(R.id.btn_send_broadcast);
        btnStartActivity.setOnClickListener(this);
        btnStartService.setOnClickListener(this);
        btnSendBroadcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_start_activity:

                Intent goToActivity=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(goToActivity);

                break;

            case R.id.btn_start_service:

                // Executed in an Activity, so 'this' is the Context
                // The fileUrl is a string URL, such as "http://www.example.com/image.png"
                Intent startService=new Intent(MainActivity.this,DownloadService.class);
                //startService.setData(Uri.parse(fileUrl));
                startService(startService);

                break;

            case R.id.btn_send_broadcast:

                Intent sendBroad=new Intent(MainActivity.this,MyBroadcastReceiver.class);
                sendBroadcast(sendBroad);

                break;
        }
    }
}
