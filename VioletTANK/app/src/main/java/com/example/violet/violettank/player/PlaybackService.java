package com.example.violet.violettank.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/9.
 */

public class PlaybackService extends Service {
    private MediaPlayer mPlayer;
    private final Binder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
    }

    public class LocalBinder extends Binder {
        public PlaybackService getService() {
            return PlaybackService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean isPlaying(){
        return true;
    }

    public void play(){
        if (mPlayer.isPlaying()){
            mPlayer.stop();
        }else {
            mPlayer.start();
        }

    }

    public void pause(){

    }
}
