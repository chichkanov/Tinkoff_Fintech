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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chichkanov.tinkoff_fintech.App;
import com.chichkanov.tinkoff_fintech.ConversationItem;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.adapters.ConversationAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConversationActivity extends AppCompatActivity {

    private final static int MESSAGE_LOADER_ID = 0;

    private static final int MESSAGE_YOU = 0;
    private static final int MESSAGE_MATE = 1;

    private List<ConversationItem> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private ConversationAdapter adapter;
    private ImageButton sendMessageButton;
    private EditText sendMessageEditText;
    private Toolbar toolbar;
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
                }
            }
        });
        initRecyclerView();
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
}
