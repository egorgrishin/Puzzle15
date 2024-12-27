package com.example.puzzle4;

public class Record {
    private final String name;
    private final int movesCount;
    private final int time;

    public Record(String name, int movesCount, int time) {
        this.name = name;
        this.movesCount = movesCount;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public int getTime() {
        return time;
    }
}
