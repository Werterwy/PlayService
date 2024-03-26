package com.example.playservice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import static android.app.Service.START_FLAG_RETRY;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Массив с идентификаторами ресурсов музыки
    private final int[] musicResources = {R.raw.charly_black, R.raw.imagine_bones, R.raw.snow};
    private final int[] imageResources = {R.drawable.left_img, R.drawable.img, R.drawable.right_img};

    private int currentTrackIndex = 0; // Индекс текущего трека в массиве
    private boolean isPlaying = false; // Флаг для отслеживания состояния воспроизведения

    private ImageButton stopPause;
/*    private ImageButton stopPause2;*/
    private boolean isPauseButton = false; // Флаг для определения состояния кнопки "Стоп/Пауза 2"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        ImageButton buttonLeft = findViewById(R.id.button_left);
        ImageButton buttonRight = findViewById(R.id.button_right);
        ImageView imageView = findViewById(R.id.imageView);
        stopPause = findViewById(R.id.stopPause);
       /* stopPause2 = findViewById(R.id.stopPause2);*/
        Button btnStop = findViewById(R.id.btn_stop);

        // Создание сервиса для воспроизведения музыки
        Intent playIntent = new Intent(this, PlayService.class);
        startService(playIntent);

        // Обработчик нажатия на кнопку "Влево"
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTrackIndex > 0) {
                    currentTrackIndex--; // Уменьшаем индекс для перехода к предыдущему треку
                } else {
                    currentTrackIndex = musicResources.length - 1; // Переходим к последнему треку в массиве
                }
                // Изменяем музыку на предыдущий трек
                Intent intent = new Intent(MainActivity.this, PlayService.class);
                intent.setAction(PlayService.ACTION_CHANGE_MUSIC);
                intent.putExtra(PlayService.EXTRA_RESOURCE_ID, musicResources[currentTrackIndex]);

                startService(intent);
                // Изменить изображение на левое
                imageView.setImageResource(imageResources[currentTrackIndex]);
            }
        });

        // Обработчик нажатия на кнопку "Вправо" (следующий трек)
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrackIndex = (currentTrackIndex + 1) % musicResources.length; // Увеличиваем индекс для перехода к следующему треку
                // Изменяем музыку на следующий трек
                Intent intent = new Intent(MainActivity.this, PlayService.class);
                intent.setAction(PlayService.ACTION_CHANGE_MUSIC);
                intent.putExtra(PlayService.EXTRA_RESOURCE_ID, musicResources[currentTrackIndex]);
                startService(intent);
                // Изменить изображение на правое
                imageView.setImageResource(imageResources[currentTrackIndex]);
            }
        });

        // Обработчик нажатия на кнопку "Стоп/Пауза"
        stopPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переключение между воспроизведением и паузой
                if (isPlaying) {
                    // Если музыка воспроизводится, остановить воспроизведение
                     pauseMusic();
                } else {
                    // Если музыка на паузе, возобновить воспроизведение
                    resumeMusic();
                }
            }
        });

        /*// Обработчик нажатия на кнопку "Стоп/Пауза 2"
        stopPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPauseButton) {
                    // Если кнопка "Стоп/Пауза 2" находится в состоянии воспроизведения, перейти в состояние паузы
                    stopPause.setImageResource(R.drawable.pause);
                    isPauseButton = true;
                } else {
                    // Если кнопка "Стоп/Пауза 2" находится в состоянии паузы, перейти в состояние воспроизведения
                    stopPause.setImageResource(R.drawable.start);
                    isPauseButton = false;
                }
            }
        });*/

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Останавливаем музыку и перематываем к началу
                Intent intent = new Intent(MainActivity.this, PlayService.class);
                intent.setAction(PlayService.ACTION_STOP_AND_REWIND);
                startService(intent);
            }
        });
    }
    // Метод для остановки воспроизведения музыки и перехода в состояние паузы
    private void pauseMusic() {
        Intent intent = new Intent(MainActivity.this, PlayService.class);
        intent.setAction(PlayService.ACTION_PAUSE_MUSIC);
        startService(intent);
        isPlaying = false;
        stopPause.setImageResource(R.drawable.start); // Изменение иконки кнопки на "Воспроизвести"
    }

    // Метод для возобновления воспроизведения музыки
    private void resumeMusic() {
        Intent intent = new Intent(MainActivity.this, PlayService.class);
        intent.setAction(PlayService.ACTION_RESUME_MUSIC);
        startService(intent);
        isPlaying = true;
        stopPause.setImageResource(R.drawable.pause); // Изменение иконки кнопки на "Пауза"
    }
}
/*@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {

    // Переменная для вашего сервиса
    private PlayService playService;

    private ImageView imageView;    
*//*    private PlayServiceListener mServiceListener;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayServiceBinder binder = (PlayServiceBinder) service;
            mServiceListener = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceListener = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
            mServiceConnection = null;
        }
    }*//*
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStop = findViewById(R.id.btn_stop);
        ImageButton buttonLeft = findViewById(R.id.button_left);
        ImageButton buttonRight = findViewById(R.id.button_right);
        ImageButton startPause = findViewById(R.id.stopPause);
        ImageButton startPause2 = findViewById(R.id.stopPause2);

        imageView = findViewById(R.id.imageView);

        playService = new PlayService();
        // Запускаем сервис
        // startService(new Intent(this, PlayService.class));


        // запуск службы
        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // используем явный вызов службы
                startService(
                        new Intent(MainActivity.this, PlayService.class));

               *//* if (view.getId() == R.id.stopPause) {
                    // Скрыть первую кнопку и показать вторую
                    ImageButton stopPause1 = findViewById(R.id.stopPause);
                    ImageButton stopPause2 = findViewById(R.id.stopPause2);
                    stopPause1.setVisibility(View.GONE);
                    stopPause2.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.stopPause2) {
                    // Скрыть вторую кнопку и показать первую
                    ImageButton stopPause1 = findViewById(R.id.stopPause);
                    ImageButton stopPause2 = findViewById(R.id.stopPause2);
                    stopPause2.setVisibility(View.GONE);
                    stopPause1.setVisibility(View.VISIBLE);
                }*//*
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
          *//*  public void onClick(View v) {
                // Останавливаем музыку и перематываем к началу
                playService.stopAndRewind();
            }*//*

            public void onClick(View view) {
                stopService(
                        new Intent(MainActivity.this, PlayService.class));
               *//* // Проверяем, какая кнопка была нажата
                if (view.getId() == R.id.stopPause) {
                    // Скрыть первую кнопку и показать вторую
                    ImageButton stopPause1 = findViewById(R.id.stopPause);
                    ImageButton stopPause2 = findViewById(R.id.stopPause2);
                    stopPause1.setVisibility(View.GONE);
                    stopPause2.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.stopPause2) {
                    // Скрыть вторую кнопку и показать первую
                    ImageButton stopPause1 = findViewById(R.id.stopPause);
                    ImageButton stopPause2 = findViewById(R.id.stopPause2);
                    stopPause2.setVisibility(View.GONE);
                    stopPause1.setVisibility(View.VISIBLE);
                }*//*
            }
        });

        // остановка службы
        startPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(
                        new Intent(MainActivity.this, PlayService.class));
               *//* // Проверяем, какая кнопка была нажата
                if (view.getId() == R.id.stopPause) {
                    // Скрыть первую кнопку и показать вторую
                    ImageButton stopPause1 = findViewById(R.id.stopPause);
                    ImageButton stopPause2 = findViewById(R.id.stopPause2);
                    stopPause1.setVisibility(View.GONE);
                    stopPause2.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.stopPause2) {
                    // Скрыть вторую кнопку и показать первую
                    ImageButton stopPause1 = findViewById(R.id.stopPause);
                    ImageButton stopPause2 = findViewById(R.id.stopPause2);
                    stopPause2.setVisibility(View.GONE);
                    stopPause1.setVisibility(View.VISIBLE);
                }*//*
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Выполняем операции в фоновом потоке
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // Останавливаем музыку и перематываем к началу
                        playService.stopAndRewind();
                        // Изменить музыку на левую
                        playService.setMusic(R.raw.snow);
                        return null;
                    }
                }.execute();
                // Изменить изображение на левое
                imageView.setImageResource(R.drawable.left_img);
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Выполняем операции в фоновом потоке
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // Останавливаем музыку и перематываем к началу
                        playService.stopAndRewind();
                        // Изменить музыку на правую
                        playService.setMusic(R.raw.charly_black);
                        return null;
                    }
                }.execute();
                // Изменить изображение на правое
                imageView.setImageResource(R.drawable.right_img);
            }
        });


    }

 *//*   public void swap(View view) {
        // Проверяем, какая кнопка была нажата
        if (view.getId() == R.id.stopPause) {
            // Скрыть первую кнопку и показать вторую
            ImageButton stopPause1 = findViewById(R.id.stopPause);
            ImageButton stopPause2 = findViewById(R.id.stopPause2);
            stopPause1.setVisibility(View.GONE);
            stopPause2.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.stopPause2) {
            // Скрыть вторую кнопку и показать первую
            ImageButton stopPause1 = findViewById(R.id.stopPause);
            ImageButton stopPause2 = findViewById(R.id.stopPause2);
            stopPause2.setVisibility(View.GONE);
            stopPause1.setVisibility(View.VISIBLE);
        }
    }*//*
}*/
