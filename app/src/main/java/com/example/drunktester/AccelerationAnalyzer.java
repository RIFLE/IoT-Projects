package com.example.drunktester;

import android.annotation.SuppressLint;

import org.jtransforms.fft.DoubleFFT_1D;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AccelerationAnalyzer {

    public static float analyzeAcceleration(List<float[]> accelerometerData) {

            double b = 9.805;
            double tau = 22.67;

            double[] xyzi = new double[accelerometerData.size()];
            for (int i = 0; i < accelerometerData.size(); i++) {
                float x = accelerometerData.get(i)[0];
                float y = accelerometerData.get(i)[1];
                float z = accelerometerData.get(i)[2];
                xyzi[i] = Math.sqrt(x * x + y * y + z * z);
            }

            // Compute the rate of change
            double[] XYZ = new double[xyzi.length];
            double delta = 0.1;
            XYZ[0] = Math.abs(xyzi[0] - xyzi[1]) / delta;
            for (int i = 1; i < xyzi.length - 1; i++) {
                XYZ[i] = Math.abs(xyzi[i - 1] - 2 * xyzi[i] + xyzi[i + 1]) / (2 * delta);
            }
            XYZ[xyzi.length - 1] = Math.abs(xyzi[xyzi.length - 2] - xyzi[xyzi.length - 1]) / delta;

            // Compute the average modulus of momentary acceleration
            double xyz_avg = 0;
            for (double xyz : xyzi) {
                xyz_avg += xyz;
            }
            xyz_avg /= xyzi.length;



            // Compute the result
            return (float) (100 * (1 / (1 + Math.exp(- tau * (xyz_avg - b)))));
    }
}