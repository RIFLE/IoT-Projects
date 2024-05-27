package com.example.drunktester;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alcotester.R;

public class AccelerometerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        Button recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(v -> startActivity(new Intent(this, RecordingAccelerometerActivity.class)));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}