/*
package com.example.playservice;
*/
/*

public interface PlayServiceListener {
    void stopAndRewind();
    void setMusic(int resourceId);
}*//*


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import android.widget.Toast;

public class PlayServiceListener extends Service */
/*implements PlayServiceListener*//*
 {

    private MediaPlayer mPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        */
/*return new PlayServiceBinder(this);*//*

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();
        mPlayer = MediaPlayer.create(this, R.raw.snow);
        mPlayer.setLooping(false);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Служба запущена",
                Toast.LENGTH_SHORT).show();
        mPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        mPlayer.stop();
    }

    // Метод для остановки музыки и перемотки к началу
   */
/* public void stopAndRewind() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.seekTo(0); // Перематываем к началу
        }
    }

    // Метод для установки новой музыки
    public void setMusic(int resourceId) {
        // Остановить предыдущую музыку
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
        }
        // Создать и запустить новый медиаплеер с новой музыкой
        mPlayer = MediaPlayer.create(this, resourceId);
        currentMusicResourceId = resourceId;
        mPlayer.setLooping(false);
        mPlayer.start();
    }*//*

}
*/
