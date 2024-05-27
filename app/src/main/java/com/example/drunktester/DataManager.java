package com.example.drunktester;

import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<float[]> accelerometerData;
    private String voiceFileName;

    private DataManager() {}

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void setAccelerometerData(List<float[]> data) {
        this.accelerometerData = data;
    }

    public List<float[]> getAccelerometerData() {
        return accelerometerData;
    }

    public void setVoiceFileName(String fileName) {
        this.voiceFileName = fileName;
    }

    public String getVoiceFileName() {
        return voiceFileName;
    }
}