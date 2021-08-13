package com.app.adoptme.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class UserPermission {

    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CONSTANT = 101;
    private static final int CAMERA_PERMISSION_CONSTANT = 102;

    private Activity activity;

    public UserPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkReadExternalStoragePermission() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestReadExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }
    }

    public boolean checkCameraPermission() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
        }
    }

}
