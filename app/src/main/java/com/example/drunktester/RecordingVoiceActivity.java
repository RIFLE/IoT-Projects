package com.example.drunktester;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.alcotester.R;

import java.io.IOException;

public class RecordingVoiceActivity extends AppCompatActivity {

    private MediaRecorder recorder;
    private String fileName;
    private Handler handler;
    private Runnable recordDataRunnable;
    private int progress;
    private ProgressBar progressBar;
    private TextView progressText;
    private Button terminateButton, backButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_voice);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        terminateButton = findViewById(R.id.terminateButton);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        terminateButton.setOnClickListener(v -> finish());
        backButton.setOnClickListener(v -> finish());
        nextButton.setOnClickListener(v -> {
            // Save the voice data and proceed to analysis
            DataManager.getInstance().setVoiceFileName(fileName);
            startActivity(new Intent(this, AnalyzeActivity.class));
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            startRecording();
        }
    }

    private void startRecording() {
        fileName = getExternalCacheDir().getAbsolutePath() + "/audiorecordtest.3gp";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();

        handler = new Handler(Looper.getMainLooper());
        progress = 0;

        recordDataRunnable = new Runnable() {
            @Override
            public void run() {
                if (progress < 5) {
                    progress++;
                    progressText.setText("Progress: " + progress + "/5 sec");
                    progressBar.setProgress(progress * 20);
                    handler.postDelayed(this, 1000);
                } else {
                    stopRecording();
                }
            }
        };

        handler.post(recordDataRunnable);
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;

        handler.removeCallbacks(recordDataRunnable);
        progressText.setText("Done!");
        terminateButton.setVisibility(Button.GONE);
        backButton.setVisibility(Button.VISIBLE);
        nextButton.setVisibility(Button.VISIBLE);
    }
}