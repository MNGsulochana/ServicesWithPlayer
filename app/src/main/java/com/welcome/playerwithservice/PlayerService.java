package com.welcome.playerwithservice;

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

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.IOException;

public class PlayerService extends Service {
    MediaPlayer player;
    SimpleExoPlayer exoPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
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
        return START_STICKY;
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