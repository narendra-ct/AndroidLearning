package com.example.multipleviewsrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: ");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new RecyclerViewAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
