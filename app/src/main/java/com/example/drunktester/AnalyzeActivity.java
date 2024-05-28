package com.example.drunktester;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alcotester.R;

import java.util.List;

import com.example.drunktester.VoiceAnalyzer;

public class AnalyzeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        TextView resultText = findViewById(R.id.resultText);
        Button retryButton = findViewById(R.id.retryButton);
        Button exitButton = findViewById(R.id.exitButton);

        List<float[]> accelerometerData = DataManager.getInstance().getAccelerometerData();
        String voiceFileName = DataManager.getInstance().getVoiceFileName();

        // Perform analysis
        float result = analyzeData(accelerometerData, voiceFileName);

        resultText.setText("Your result is: you are drunk by " + result + "%");

        // Retry button to start the test again
        retryButton.setOnClickListener(v -> {
            DataManager.getInstance().clearData();
            Intent intent = new Intent(AnalyzeActivity.this, AccelerometerActivity.class);
            startActivity(intent);
            finish();
        });

        // Exit button to close the app
        exitButton.setOnClickListener(v -> finishAffinity());
    }

    private float analyzeData(List<float[]> accelerometerData, String voiceFileName) {
        // Compute the result
        float acceleration_result = AccelerationAnalyzer.analyzeAcceleration(accelerometerData);

        float voice_scale = VoiceAnalyzer.analyzeVoice(voiceFileName);

        return voice_scale * 0.2f +  acceleration_result * 0.8f; // Replace with actual result
    }
}