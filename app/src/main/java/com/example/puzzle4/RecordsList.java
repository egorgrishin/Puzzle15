package com.example.puzzle4;

import java.util.ArrayList;
import java.util.Comparator;

public class RecordsList {

    private final ArrayList<Record> records;

    public RecordsList() {
        records = new ArrayList<>();
    }

    public void add(Record record) {
        records.add(record);
    }

    public void sortByMovesCount(boolean reverse) {
        Comparator<Record> comparator = Comparator.comparingInt(Record::getMovesCount);
        if (reverse) {
            comparator = comparator.reversed();
        }
        records.sort(comparator);
    }

    public void sortByTime(boolean reverse) {
        Comparator<Record> comparator = Comparator.comparingInt(Record::getTime);
        if (reverse) {
            comparator = comparator.reversed();
        }
        records.sort(comparator);
    }

    public ArrayList<Record> getRecords() {
        return new ArrayList<>(records);
    }
}
