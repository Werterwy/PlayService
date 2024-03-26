package com.example.playservice;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class PlayService extends Service {

    private MediaPlayer mPlayer;
    public static final String ACTION_CHANGE_MUSIC = "com.example.myapp.action.CHANGE_MUSIC";
    public static final String EXTRA_RESOURCE_ID = "resourceId";

    public static final String ACTION_PAUSE_MUSIC = "com.example.myapp.action.PAUSE_MUSIC";
    public static final String ACTION_RESUME_MUSIC = "com.example.myapp.action.RESUME_MUSIC";

    public static final String ACTION_STOP_AND_REWIND = "com.example.playservice.action.STOP_AND_REWIND";

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        PlayService getService() {
            // Return this instance of PlayService so clients can call public methods
            return PlayService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Создаем MediaPlayer для воспроизведения музыки
        mPlayer = MediaPlayer.create(this, R.raw.imagine_bones);
        mPlayer.setLooping(true); // Устанавливаем повторное воспроизведение
    }

  /*  @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals(ACTION_CHANGE_MUSIC)) {
                // Получаем новый ресурс музыки из Intent
                int resourceId = intent.getIntExtra(EXTRA_RESOURCE_ID, 0);
                // Изменяем музыку для воспроизведения
                changeMusic(resourceId);
            }
        }
        return START_STICKY;
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_PAUSE_MUSIC:
                        pauseMusic();
                        break;
                    case ACTION_RESUME_MUSIC:
                        resumeMusic();
                        break;
                    case ACTION_CHANGE_MUSIC:
                        int resourceId = intent.getIntExtra(EXTRA_RESOURCE_ID, 0);
                        changeMusic(resourceId);
                        break;
                    case ACTION_STOP_AND_REWIND:
                        stopAndRewind();
                        break;
                    // Другие обработчики действий...
                }
            }
        }
        return START_STICKY;
    }

    // Метод для остановки воспроизведения музыки и перехода в состояние паузы
    private void pauseMusic() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    // Метод для возобновления воспроизведения музыки
    private void resumeMusic() {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    // Метод для изменения музыки
    private void changeMusic(int resourceId) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resourceId);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Останавливаем воспроизведение музыки и освобождаем ресурсы MediaPlayer
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
    // Метод для остановки воспроизведения музыки
    public void stopMusic() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    // Метод для остановки музыки и перемотки к началу
    private void stopAndRewind() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.seekTo(0);
        }
    }

    // Метод для изменения музыки
    /*public void changeMusic(int resourceId) {
        // Останавливаем воспроизведение текущей музыки
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
        }
        // Создаем новый MediaPlayer с указанным ресурсом
        mPlayer = MediaPlayer.create(this, resourceId);
        mPlayer.setLooping(true); // Устанавливаем повторное воспроизведение
        mPlayer.start(); // Начинаем воспроизведение новой музыки
    }*/
}
/*import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import android.widget.Toast;

public class PlayService extends Service *//*implements PlayServiceListener*//* {

    private MediaPlayer mPlayer;
    private int currentMusicResourceId;

    @Override
    public IBinder onBind(Intent intent) {
        *//*return new PlayServiceBinder(this);*//*
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();
        mPlayer = MediaPlayer.create(this, R.raw.imagine_bones);
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
    public void stopAndRewind() {
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
    }
}*/
