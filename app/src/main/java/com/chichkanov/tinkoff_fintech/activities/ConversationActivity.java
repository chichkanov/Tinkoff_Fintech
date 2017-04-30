package com.chichkanov.tinkoff_fintech.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chichkanov.tinkoff_fintech.App;
import com.chichkanov.tinkoff_fintech.ConversationItem;
import com.chichkanov.tinkoff_fintech.DbContract;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.adapters.ConversationAdapter;
import com.chichkanov.tinkoff_fintech.models.DialogsItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConversationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ConversationItem>> {

    private final static int MESSAGE_LOADER_ID = 0;

    private static final int MESSAGE_YOU = 0;
    private static final int MESSAGE_MATE = 1;

    private List<ConversationItem> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private ConversationAdapter adapter;
    private ImageButton sendMessageButton;
    private EditText sendMessageEditText;
    private Toolbar toolbar;
    private MessageLoader messageLoader;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        userName = getIntent().getExtras().getString("username", "Anonymous");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(userName);

        sendMessageButton = (ImageButton) findViewById(R.id.button_send_message);
        sendMessageEditText = (EditText) findViewById(R.id.edit_text_send_message);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendMessageEditText.getText().length() > 0 && sendMessageEditText.getText().toString().trim().length() > 0) {
                    String text = sendMessageEditText.getText().toString().trim();
                    addMessage(text);
                }
            }
        });
        initRecyclerView();
        messageLoader = new MessageLoader(this, userName);
        getSupportLoaderManager().initLoader(MESSAGE_LOADER_ID, null, this);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_conversation);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addMessage(String text) {
        SQLiteDatabase writableDatabase = App.getDbhelper().getWritableDatabase();

        String date = new Date().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.MessageEntry.COLUMN_AUTHOR_ID, MESSAGE_YOU);
        contentValues.put(DbContract.MessageEntry.COLUMN_DIALOG_ID, userName);
        contentValues.put(DbContract.MessageEntry.COLUMN_TEXT, text);
        contentValues.put(DbContract.MessageEntry.COLUMN_TIMESTAMP, date);

        writableDatabase.insert(DbContract.MessageEntry.TABLE_NAME, null, contentValues);
        sendMessageEditText.setText("");
        getSupportLoaderManager().restartLoader(MESSAGE_LOADER_ID, null, this);

        ContentValues cv = new ContentValues();
        cv.put(DbContract.DialogEntry.COLUMN_DESCRIPTION, text);
        cv.put(DbContract.DialogEntry.COLUMN_DESCRIPTION_DATE, date);
        writableDatabase.update(DbContract.DialogEntry.TABLE_NAME, cv, DbContract.DialogEntry.COLUMN_TITLE + "= ?", new String[]{userName});
    }

    @Override
    public Loader<List<ConversationItem>> onCreateLoader(int id, Bundle args) {
        return new MessageLoader(this, userName);
    }

    @Override
    public void onLoadFinished(Loader<List<ConversationItem>> loader, List<ConversationItem> data) {
        adapter.setItems(data);
        list = data;
    }

    @Override
    public void onLoaderReset(Loader<List<ConversationItem>> loader) {
        adapter.notifyDataSetChanged();
    }

    public static class MessageLoader extends AsyncTaskLoader<List<ConversationItem>> {

        private List<ConversationItem> data;
        private String userName;

        public MessageLoader(Context context, String userName) {
            super(context);
            this.userName = userName;
        }

        @Override
        public List<ConversationItem> loadInBackground() {
            List<ConversationItem> data = getPreviousDialogItems();
            return data;
        }

        @Override
        public void deliverResult(List<ConversationItem> newData) {
            if (isReset()) {
                if (data != null) {
                    onReleaseResources(data);
                }
            }

            List<ConversationItem> oldData = data;
            data = newData;

            if (isStarted()) {
                super.deliverResult(newData);
            }

            if (oldData != null) {
                onReleaseResources(oldData);
            }
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

        @Override
        public void onCanceled(List<ConversationItem> data) {
            super.onCanceled(data);
            onReleaseResources(data);
        }

        @Override
        protected void onReset() {
            super.onReset();
            onStopLoading();
            if (data != null) {
                onReleaseResources(data);
                data = null;
            }
        }

        private void onReleaseResources(List<ConversationItem> data) {

        }

        @NonNull
        private ArrayList<ConversationItem> getPreviousDialogItems() {
            SQLiteDatabase readableDatabase = App.getDbhelper().getReadableDatabase();
            Cursor cursor = readableDatabase.query(DbContract.MessageEntry.TABLE_NAME,
                    new String[]{
                            DbContract.MessageEntry.COLUMN_AUTHOR_ID,
                            DbContract.MessageEntry.COLUMN_TIMESTAMP,
                            DbContract.MessageEntry.COLUMN_TEXT,
                            DbContract.MessageEntry.COLUMN_DIALOG_ID
                    },
                    null,
                    null,
                    null,
                    null,
                    null);
            ArrayList<ConversationItem> dialogItems = new ArrayList<>();
            while (cursor.moveToNext()) {
                int textIndex = cursor.getColumnIndex(DbContract.MessageEntry.COLUMN_TEXT);
                int timeIndex = cursor.getColumnIndex(DbContract.MessageEntry.COLUMN_TIMESTAMP);
                int dialogIndex = cursor.getColumnIndex(DbContract.MessageEntry.COLUMN_DIALOG_ID);

                String text = cursor.getString(textIndex);

                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String time = dateFormat.format(new Date(cursor.getString(timeIndex)));

                String dialog = cursor.getString(dialogIndex);

                if (dialog.equals(userName)) dialogItems.add(new ConversationItem(text, time, 0));
            }
            cursor.close();
            return dialogItems;
        }
    }
}
