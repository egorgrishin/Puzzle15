package com.example.puzzle4;

import android.content.Intent;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class Navigator {

    public static void setBackHandler(AppCompatActivity context, Class<?> cls) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent mainActivityIntent = new Intent(context, cls);
                context.startActivity(mainActivityIntent);
            }
        };

        context.getOnBackPressedDispatcher().addCallback(context, callback);
    }
}
