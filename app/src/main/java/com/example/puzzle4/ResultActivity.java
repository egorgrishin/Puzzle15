package com.example.puzzle4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private String name;
    private int movesCount;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initActivity();
        new Storage(this).saveRecord(new Record(name, movesCount, time));
        Navigator.setBackHandler(this, MainActivity.class);
    }

    private void initActivity() {
        name = getIntent().getStringExtra("name");
        movesCount = getIntent().getIntExtra("movesCount", -1);
        time = getIntent().getIntExtra("time", -1);

        ((TextView) findViewById(R.id.movesCountText)).setText(String.format("Ходы: %d", movesCount));
        ((TextView) findViewById(R.id.timeText)).setText(String.format("Время: %d", time));

        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
        });
    }
}
