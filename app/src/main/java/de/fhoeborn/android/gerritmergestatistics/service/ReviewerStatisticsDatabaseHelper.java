package de.fhoeborn.android.gerritmergestatistics.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewerStatisticsDatabaseHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;

    public ReviewerStatisticsDatabaseHelper(Context context) {
        super(context, "reviewer.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReviewerStatisticsDatabase.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Nothing to do ATM
    }
}
