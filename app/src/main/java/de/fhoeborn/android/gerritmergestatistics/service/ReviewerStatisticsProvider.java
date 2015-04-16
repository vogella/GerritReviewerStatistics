package de.fhoeborn.android.gerritmergestatistics.service;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ReviewerStatisticsProvider extends ContentProvider {
    public static final Uri BASE_URI = Uri.parse("content://de.fhoeborn.android.gerrit/");

    private ReviewerStatisticsDatabaseHelper databaseHelper;

    public ReviewerStatisticsProvider() {
    }

    @Override
    public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletedRows = databaseHelper.getWritableDatabase().delete(ReviewerStatisticsDatabase.TABLE_NAME, selection, selectionArgs);
        this.getContext().getContentResolver().notifyChange(BASE_URI, null);

        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public synchronized Uri insert(Uri uri, ContentValues values) {
        databaseHelper.getWritableDatabase().insert(ReviewerStatisticsDatabase.TABLE_NAME, null, values);
        this.getContext().getContentResolver().notifyChange(BASE_URI, null);

        return BASE_URI;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new ReviewerStatisticsDatabaseHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = databaseHelper.getWritableDatabase().query(ReviewerStatisticsDatabase.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);

        return cursor;
    }

    @Override
    public synchronized int update(Uri uri, ContentValues values, String selection,
                                   String[] selectionArgs) {
        int updatedRows = databaseHelper.getWritableDatabase().update(ReviewerStatisticsDatabase.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(BASE_URI, null);
        return updatedRows;
    }
}
