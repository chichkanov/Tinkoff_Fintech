package com.chichkanov.tinkoff_fintech.conversation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chichkanov.tinkoff_fintech.R;

import java.util.ArrayList;
import java.util.List;

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
