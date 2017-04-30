package com.chichkanov.tinkoff_fintech;

import android.provider.BaseColumns;


public class DbContract {
    static final String CREATE_DIALOG_SCRIPT = "CREATE TABLE " + DialogEntry.TABLE_NAME + " (" +
            DialogEntry._ID + " INTEGER PRIMARY KEY," +
            DialogEntry.COLUMN_TITLE + " TEXT," +
            DialogEntry.COLUMN_TIMESTAMP + " INTEGER," +
            DialogEntry.COLUMN_DESCRIPTION + " TEXT" +
            " )";

    private DbContract() {
        //no instance
    }

    public static final class DialogEntry implements BaseColumns {
        public static final String TABLE_NAME = "dialogs";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
