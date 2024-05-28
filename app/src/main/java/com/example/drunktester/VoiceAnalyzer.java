package com.example.drunktester;

import android.annotation.SuppressLint;

import org.jtransforms.fft.DoubleFFT_1D;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class VoiceAnalyzer {

    public static float analyzeVoice(String voiceFileName) {
        try {
            // Read the audio file
            byte[] audioBytes = readAudioFile(voiceFileName);

            // Convert bytes to double for FFT
            double[] audioData = convertToDoubleArray(audioBytes);

            // Perform FFT
            DoubleFFT_1D fft = new DoubleFFT_1D(audioData.length);
            fft.realForward(audioData);

            // Calculate power spectrum
            double[] powerSpectrum = calculatePowerSpectrum(audioData);

            // Analyze power in specified frequency lanes
            return analyzeFrequencyLanes(powerSpectrum);

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static byte[] readAudioFile(String voiceFileName) throws IOException {
        File file = new File(voiceFileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    private static double[] convertToDoubleArray(byte[] audioBytes) {
        double[] audioData = new double[audioBytes.length];
        for (int i = 0; i < audioBytes.length; i++) {
            audioData[i] = (double) audioBytes[i];
        }
        return audioData;
    }

    private static double[] calculatePowerSpectrum(double[] audioData) {
        int n = audioData.length / 2;
        double[] powerSpectrum = new double[n];
        for (int i = 0; i < n; i++) {
            double real = audioData[2 * i];
            double imag = audioData[2 * i + 1];
            powerSpectrum[i] = Math.sqrt(real * real + imag * imag);
        }
        return powerSpectrum;
    }

    private static float analyzeFrequencyLanes(double[] powerSpectrum) {
        int sampleRate = 44100; // Change if your sample rate is different
        int n = powerSpectrum.length;

        // Frequency resolution
        double freqResolution = (double) sampleRate / n;

        // Define lanes
        int[] lanes = {60, 90, 120, 150, 180, 210, 240, 270, 300};
        double[] lanePowers = new double[lanes.length - 1];

        // Calculate power in each lane
        for (int i = 0; i < lanes.length - 1; i++) {
            int startFreq = lanes[i];
            int endFreq = lanes[i + 1];
            int startIndex = (int) (startFreq / freqResolution);
            int endIndex = (int) (endFreq / freqResolution);

            for (int j = startIndex; j < endIndex; j++) {
                lanePowers[i] += powerSpectrum[j];
            }
        }

        // Find the most powerful lane
        double maxPower = 0;
        for (double power : lanePowers) {
            if (power > maxPower) {
                maxPower = power;
            }
        }

        double centralLanePower = 0;
        int centralLaneIndex = 0;

        for (int i = 0; i < lanePowers.length; i++) {
            if (lanePowers[i] == maxPower) {
                centralLanePower = lanePowers[i];
                centralLaneIndex = i;
                break;
            }
        }

        // Compare power of other lanes with the central lane
        int count = 0;
        for (int i = 0; i < lanePowers.length; i++) {
            if (i != centralLaneIndex && lanePowers[i] >= 0.75 * centralLanePower) {
                count++;
            }
        }

        // Calculate the result
        if (count >= 5) {
            return 1;
        } else if (count == 4) {
            return 0.8f;
        } else if (count == 3) {
            return 0.75f;
        } else if (count == 2) {
            return 0.5f;
        } else {
            return 0;
        }
    }
}