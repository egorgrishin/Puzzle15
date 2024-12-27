package com.example.puzzle4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStatistics).setOnClickListener(v -> {
            Intent statisticActivityIntent = new Intent(this, StatisticActivity.class);
            startActivity(statisticActivityIntent);
        });

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            Intent mainActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(mainActivityIntent);
        });
    }
}