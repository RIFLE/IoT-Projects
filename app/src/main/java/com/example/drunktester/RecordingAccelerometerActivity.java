package com.example.drunktester;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alcotester.R;

import java.util.ArrayList;
import java.util.List;

public class RecordingAccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private List<float[]> accelerometerData;
    private Handler handler;
    private Runnable recordDataRunnable;
    private int progress;
    private ProgressBar progressBar;
    private TextView progressText;
    private Button terminateButton, backButton, nextButton;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_accelerometer);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        terminateButton = findViewById(R.id.terminateButton);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometerData = new ArrayList<>();
        lastUpdate = 0;

        terminateButton.setOnClickListener(v -> finish());
        backButton.setOnClickListener(v -> finish());
        nextButton.setOnClickListener(v -> {
            // Save the accelerometer data and proceed to voice recording
            DataManager.getInstance().setAccelerometerData(accelerometerData);
            startActivity(new Intent(this, VoiceRecordActivity.class));
        });

        startRecording();
    }

    private void startRecording() {
        handler = new Handler(Looper.getMainLooper());
        progress = 0;

        recordDataRunnable = new Runnable() {
            @Override
            public void run() {
                if (progress < 10) {
                    progress++;
                    progressText.setText("Progress: " + progress + "/10 sec");
                    progressBar.setProgress(progress * 10);
                    handler.postDelayed(this, 1000);
                } else {
                    stopRecording();
                }
            }
        };

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        handler.post(recordDataRunnable);
    }

    private void stopRecording() {
        sensorManager.unregisterListener(this);
        handler.removeCallbacks(recordDataRunnable);
        progressText.setText("Done!");
        terminateButton.setVisibility(Button.GONE);
        backButton.setVisibility(Button.VISIBLE);
        nextButton.setVisibility(Button.VISIBLE);

        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdate >= 100) {
                lastUpdate = currentTime;
                accelerometerData.add(new float[]{event.values[0], event.values[1], event.values[2]});
                if (accelerometerData.size() >= 100) {
                    stopRecording();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}