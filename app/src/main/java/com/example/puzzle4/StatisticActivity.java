package com.example.puzzle4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private RecordsList statistic;
    private boolean isReverseMoves = false;
    private boolean isReverseTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        tableLayout = findViewById(R.id.tableLayout);
        Button sortByMovesButton = findViewById(R.id.sort_by_moves_button);
        Button sortByTimeButton = findViewById(R.id.sort_by_time_button);

        statistic = new Storage(this).getStatistic();
        displayStatistic();

        sortByMovesButton.setOnClickListener(v -> {
            statistic.sortByMovesCount(isReverseMoves);
            isReverseMoves = !isReverseMoves;
            displayStatistic();
        });

        sortByTimeButton.setOnClickListener(v -> {
            statistic.sortByTime(isReverseTime);
            isReverseTime = !isReverseTime;
            displayStatistic();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
        });
    }

    private void displayStatistic() {
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(this);
        addTextToRow(headerRow, "Место", 1);
        addTextToRow(headerRow, "Ходы", 1);
        addTextToRow(headerRow, "Время", 1);
        addTextToRow(headerRow, "Имя", 2);
        tableLayout.addView(headerRow);

        int i = 1;
        for (Record record : statistic.getRecords()) {
            TableRow row = new TableRow(this);
            addTextToRow(row, String.valueOf(i), 1);
            addTextToRow(row, String.valueOf(record.getMovesCount()), 1);
            addTextToRow(row, String.valueOf(record.getTime()), 1);
            addTextToRow(row, record.getName(), 2);
            tableLayout.addView(row);
            ++i;
        }
    }

    private void addTextToRow(TableRow row, String text, int weight) {
        TableRow.LayoutParams l = new TableRow.LayoutParams();
        l.width = 0;
        l.weight = weight;

        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(l);
        textView.setGravity(Gravity.CENTER);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxLines(1);
        textView.setPadding(0, 0, 0, 24);
        row.addView(textView);
    }
}
