package com.example.puzzle4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private String name;
    private int size = 4;
    private Button[][] buttons;
    private ArrayList<Integer> tiles;
    private int emptyRow;
    private int emptyCol;
    private int moveCount = 0;
    private long startTime;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private TextView moveCountView;
    private TextView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        name = getIntent().getStringExtra("name");
        size = getIntent().getIntExtra("size", 4);

        buttons = new Button[size][size];
        tiles = new ArrayList<>();

        moveCountView = findViewById(R.id.moviesCount);
        timerView = findViewById(R.id.timer);

        setupGame();

        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> finish());

        Button newGameButton = findViewById(R.id.btnRestart);
        newGameButton.setOnClickListener(v -> setupGame());

        startTimer();

        Navigator.setBackHandler(this, MainActivity.class);
    }

    private void setupGame() {
        moveCount = 0;
        updateMoveCount();
        tiles.clear();

        for (int i = 1; i < size * size; i++) {
            tiles.add(i);
        }
        tiles.add(0);
        Collections.shuffle(tiles);

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        GridLayout gridLayout = findViewById(R.id.grid_layout);

        linearLayout.post(() -> {
            int buttonSize = Math.min(linearLayout.getWidth(), linearLayout.getHeight()) / size;

            gridLayout.removeAllViews();
            gridLayout.setRowCount(size);
            gridLayout.setColumnCount(size);

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    int index = row * size + col;
                    int value = tiles.get(index);

                    Button button = new Button(GameActivity.this);
                    button.setTextSize(24);
                    button.setTextColor(Color.WHITE);

                    Drawable background = ContextCompat.getDrawable(this, R.drawable.rounded_button);
                    button.setBackground(background);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = buttonSize;
                    params.height = buttonSize;
                    button.setLayoutParams(params);

                    button.setText(value == 0 ? "" : String.valueOf(value));
                    button.setTag(new int[]{row, col});
                    button.setOnClickListener(GameActivity.this::onTileClick);
                    buttons[row][col] = button;
                    gridLayout.addView(button);

                    if (value == 0) {
                        emptyRow = row;
                        emptyCol = col;
                    }
                }
            }

            startTimer();
        });
    }

    private void onTileClick(View view) {
        Button button = (Button) view;
        int[] position = (int[]) button.getTag();
        int row = position[0];
        int col = position[1];

        if ((Math.abs(row - emptyRow) == 1 && col == emptyCol) || (Math.abs(col - emptyCol) == 1 && row == emptyRow)) {
            moveTile(row, col);
            moveCount++;
            updateMoveCount();

            if (isWin()) {
                stopTimer();
                Intent resultActivityIntent = new Intent(this, ResultActivity.class);
                resultActivityIntent.putExtra("name", name);

                long elapsed = System.currentTimeMillis() - startTime;
                int seconds = (int) (elapsed / 1000);
                resultActivityIntent.putExtra("time", seconds);
                resultActivityIntent.putExtra("movesCount", moveCount);

                startActivity(resultActivityIntent);
            }
        }
    }

    private void moveTile(int row, int col) {
        buttons[emptyRow][emptyCol].setText(buttons[row][col].getText());
        buttons[row][col].setText("");

        tiles.set(emptyRow * size + emptyCol, tiles.get(row * size + col));
        tiles.set(row * size + col, 0);

        emptyRow = row;
        emptyCol = col;
    }

    private boolean isWin() {
        for (int i = 0; i < tiles.size() - 1; i++) {
            if (tiles.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }

    private void updateMoveCount() {
        moveCountView.setText(String.format(Locale.getDefault(), "Ходы: %d", moveCount));
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        timerHandler = new Handler(Looper.getMainLooper());
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                int seconds = (int) (elapsed / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                timerView.setText(String.format(Locale.getDefault(), "Время: %02d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
