package com.chichkanov.tinkoff_fintech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ImageButton sendMessageButton;
    private EditText sendMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        sendMessageButton = (ImageButton) findViewById(R.id.button_send_message);
        sendMessageEditText = (EditText) findViewById(R.id.edit_text_send_message);
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_conversation);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationAdapter(createDataset());
        recyclerView.setAdapter(adapter);
    }

    private List<ConversationItem> createDataset() {
        List<ConversationItem> list = new ArrayList<>();
        list.add(new ConversationItem("Hello", null, 0));
        list.add(new ConversationItem(null, "Hi", 1));
        list.add(new ConversationItem("I am fine", null, 0));
        list.add(new ConversationItem("And you?", null, 0));
        list.add(new ConversationItem(null, "Yesterday i was in the cinema and the film was great", 1));
        list.add(new ConversationItem("Hello", null, 0));
        list.add(new ConversationItem(null, "Hi", 1));
        list.add(new ConversationItem("I am fine", null, 0));
        list.add(new ConversationItem("And you?", null, 0));
        list.add(new ConversationItem(null, "Yesterday i was in the cinema and the film was great", 1));
        list.add(new ConversationItem("Hello", null, 0));
        list.add(new ConversationItem(null, "Hi", 1));
        list.add(new ConversationItem("I am fine", null, 0));
        list.add(new ConversationItem("And you?", null, 0));
        list.add(new ConversationItem(null, "Yesterday i was in the cinema and the film was great", 1));
        return list;
    }
}
