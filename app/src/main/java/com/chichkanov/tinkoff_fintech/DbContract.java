package com.chichkanov.tinkoff_fintech;

import android.provider.BaseColumns;


public class DbContract {
    static final String CREATE_DIALOG_SCRIPT = "CREATE TABLE " + DialogEntry.TABLE_NAME + " (" +
            DialogEntry._ID + " INTEGER PRIMARY KEY," +
            DialogEntry.COLUMN_TITLE + " TEXT," +
            DialogEntry.COLUMN_TIMESTAMP + " INTEGER," +
            DialogEntry.COLUMN_DESCRIPTION + " TEXT" +
            " )";

    static final String CREATE_MESSAGE_SCRIPT = "CREATE TABLE " + DialogEntry.TABLE_NAME + " (" +
            Messages._ID + " INTEGER PRIMARY KEY," +
            Messages.COLUMN_AUTHOR_ID + " TEXT," +
            Messages.COLUMN_TIMESTAMP + " INTEGER," +
            Messages.COLUMN_TEXT + " TEXT" +
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

    public static final class Messages implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_AUTHOR_ID = "author";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_TEXT = "text";
    }
}
