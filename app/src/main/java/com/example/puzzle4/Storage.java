package com.example.puzzle4;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class Storage {

    private final SharedPreferences storage;
    private final static String FILE = "app";
    private final static String NAME = "name";
    private final static String STATISTIC = "statistic";

    public Storage(Context context) {
        storage = context
                .getApplicationContext()
                .getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public RecordsList getStatistic() {
        String jsonStatistic = storage.getString(STATISTIC, "{}");
        Gson gson = new Gson();
        return gson.fromJson(jsonStatistic, RecordsList.class);
    }

    public void saveRecord(Record record) {
        String jsonStatistic = storage.getString(STATISTIC, "{}");

        Gson gson = new Gson();
        RecordsList statistic = gson.fromJson(jsonStatistic, RecordsList.class);
        statistic.add(record);

        Editor editor = storage.edit();
        editor.putString(STATISTIC, gson.toJson(statistic));
        editor.apply();
    }

    public String getName() {
        return storage.getString(NAME, "");
    }

    public void saveName(String name) {
        Editor editor = storage.edit();
        editor.putString(NAME, name);
        editor.apply();
    }
}
