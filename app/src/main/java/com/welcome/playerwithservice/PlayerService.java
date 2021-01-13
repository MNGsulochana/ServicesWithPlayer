package com.welcome.playerwithservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import static com.welcome.playerwithservice.MyNotifyPlayerApp.CHANNEL_ID;
import java.io.IOException;

public class PlayerService extends Service {
    MediaPlayer player;
    SimpleExoPlayer exoPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
   // static String CHANNEL_ID="FOREGROUNDsERVICE";
    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
   // https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4
   // https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service","SERvoice clled");

        /**
         * with mediaplayer
         */
    /* player=new MediaPlayer().create(this, Settings.System.DEFAULT_RINGTONE_URI);
      player.start();*/
        initializePlayer1();
        sendNotification();
        return START_STICKY;
    }

    /**
     * create notification channel like this or else we can create the channel for entire app
     */

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
    private void sendNotification() {
        Log.d("geertnoti","notifymeee");
        //createNotificationChannel();
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        Notification notification= new NotificationCompat.Builder(this,CHANNEL_ID).setContentText("Playerwelcome")
                .setContentTitle("hiii").setContentIntent(pendingIntent).setSmallIcon(R.drawable.notify).build();
        startForeground(1,notification);

    }

    private void initializePlayer1() {
       exoPlayer=new SimpleExoPlayer.Builder(this).build();
        MediaItem mediaItem=MediaItem.fromUri(Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"));
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare();
        exoPlayer.play();

    }
    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  player.stop(); // with mediaplayer
        releasePlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}