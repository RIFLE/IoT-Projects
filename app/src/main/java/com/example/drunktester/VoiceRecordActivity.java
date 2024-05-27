package com.example.drunktester;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alcotester.R;

public class VoiceRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_record);

        Button recordVoiceButton = findViewById(R.id.recordVoiceButton);
        recordVoiceButton.setOnClickListener(v -> startActivity(new Intent(this, RecordingVoiceActivity.class)));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}