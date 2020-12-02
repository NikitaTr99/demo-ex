package com.ntsoftware.vspc.demoex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ntsoftware.vspc.demoex.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container,new MainFragment()).commit();
    }
}