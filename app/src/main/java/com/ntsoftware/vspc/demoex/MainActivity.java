package com.ntsoftware.vspc.demoex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntsoftware.vspc.demoex.ui.edit.PeopleEditActivity;
import com.ntsoftware.vspc.demoex.ui.home.MainFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton add_fab;
    private MainFragment main_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_fab = findViewById(R.id.add_fab);

        main_fragment = new MainFragment();

        add_fab.setOnClickListener(fab_onclick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, main_fragment).commit();
    }

    View.OnClickListener fab_onclick = view -> {
        Intent intent = new Intent(view.getContext(), PeopleEditActivity.class);
        view.getContext().startActivity(intent);
    };
}