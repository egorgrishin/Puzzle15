package com.example.puzzle4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Storage storage;
    private int size = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        storage = new Storage(this);

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            Intent mainActivityIntent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
        });

        RadioGroup sizeSelector = findViewById(R.id.sizeSelector);
        sizeSelector.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.size3x3) {
                size = 3;
            } else if (checkedId == R.id.size4x4) {
                size = 4;
            } else if (checkedId == R.id.size5x5) {
                size = 5;
            }
        });

        EditText nameInput = findViewById(R.id.name);
        nameInput.setText(storage.getName());

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            String name = String.valueOf(nameInput.getText());
            if (name.isEmpty()) {
                Toast.makeText(this, "Укажите имя!", Toast.LENGTH_SHORT).show();
                return;
            }

            storage.saveName(name);
            Intent gameActivityIntent = new Intent(SettingsActivity.this, GameActivity.class);
            gameActivityIntent.putExtra("name", name);
            gameActivityIntent.putExtra("size", size);
            startActivity(gameActivityIntent);
        });
    }
}
