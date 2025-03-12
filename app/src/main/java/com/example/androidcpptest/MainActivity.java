package com.example.androidcpptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcpptest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'androidcpptest' library on application startup.
    static {
        System.loadLibrary("androidcpptest");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        binding.sendBc.setOnClickListener(v -> {
            SSDPHelper helper = new SSDPHelper(message -> {
                Log.e("MainActivity", "接收数据" + message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            });
            helper.discoverDevices();
        });
        binding.btnWeb.setOnClickListener(v -> {
            startActivity(new Intent(this, WebViewActivity.class));
        });
    }

    public native String stringFromJNI();
}