package de.fhoeborn.android.gerritmergestatistics.service;

public interface ReviewerStatisticsDatabase {
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REVIEWER_NAME = "Reviewer_name";
    public static final String COLUMN_AMOUNT = "amount";

    public static final String TABLE_NAME = "ReviewerStatistics";
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_REVIEWER_NAME + " TEXT, " + COLUMN_AMOUNT + " INTEGER)";
}
