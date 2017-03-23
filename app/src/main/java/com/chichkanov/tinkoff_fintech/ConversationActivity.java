package com.chichkanov.tinkoff_fintech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    final List<ConversationItem> list = new ArrayList<>();

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
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendMessageEditText.getText().length() > 0) {
                    list.add(0, new ConversationItem(sendMessageEditText.getText().toString(), 0));
                    adapter.notifyItemInserted(0);
                    sendMessageEditText.setText("");
                    recyclerView.scrollToPosition(0);
                }
            }
        });
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
        list.add(new ConversationItem("вфывфывфывфыафыафывфывыфа фыв фыв фыв фы вфывфывыф", 0));
        list.add(new ConversationItem("ЛФтвлфывтлфвлыфвы", 1));
        list.add(new ConversationItem("фывыфаыфафыафыа\n" +
                "ФЫвфывфыаФЫа", 0));
        list.add(new ConversationItem("ВФЫвыфвфывфыв!", 1));
        list.add(new ConversationItem("ФЫВфывфывыв —\n" +
                "ВФЫПыфвыфвфывыфв", 0));
        list.add(new ConversationItem("ФЫВЫФВЫФВФЫаыфаф вфывыфвыфв вфывыфвфыв", 1));
        list.add(new ConversationItem("йцудйцзхдзмб", 1));
        list.add(new ConversationItem("мябяючтпшрйзчпзопв", 1));
        list.add(new ConversationItem("ыфвыфвыфафывыфвыфв", 0));
        list.add(new ConversationItem("пкуварвар", 1));
        list.add(new ConversationItem("автавьпзькдж", 0));
        list.add(new ConversationItem("пькуджп.", 1));
        list.add(new ConversationItem("аывщьпджьвыжды\n" +
                "Дыватщвыьтаждвы", 0));
        list.add(new ConversationItem("м сьчсьи чсьбм", 1));
        return list;
    }
}
