package com.example.drunktester;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alcotester.R;

import java.util.List;

public class AnalyzeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        TextView resultText = findViewById(R.id.resultText);

        List<float[]> accelerometerData = DataManager.getInstance().getAccelerometerData();
        String voiceFileName = DataManager.getInstance().getVoiceFileName();

        // Perform analysis
        float result = analyzeData(accelerometerData, voiceFileName);

        resultText.setText("Your result is: you are drunk by " + result + "%");
    }

    private float analyzeData(List<float[]> accelerometerData, String voiceFileName) {
        // Implement your analysis logic here
        // For example, calculate the variance or some other metric
        return 0; // Replace with actual result
    }
}
