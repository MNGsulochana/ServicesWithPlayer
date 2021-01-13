package com.welcome.playerwithservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    Button but,notifyb;
    static String CHANNEL_ID="FOREGROUNDsERVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but=findViewById(R.id.b1);
        notifyb=findViewById(R.id.notifybutton);
        notifyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  createNotificationChannel();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,0);
                Notification notification= new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID).setContentText("welcome")
                        .setContentTitle("hiii").setContentIntent(pendingIntent).setSmallIcon(R.drawable.notify).build();
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1,notification);
               // notification.notify();

            }
        });
        intent =new Intent(MainActivity.this,PlayerService.class);
        but.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Log.d("clicked","button");
                /**
                 * call service like this
                 * if the app is in foreground then only its playing and disply notification
                 */
                startService(intent);
                /**
                 * call service like this
                 * if the app is in background(if we close app also) and foreground its playing and disply notification
                 */
             // startForegroundService(intent);
            }
        });


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(intent);
    }
}