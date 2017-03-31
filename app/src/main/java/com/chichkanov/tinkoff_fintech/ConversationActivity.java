package com.chichkanov.tinkoff_fintech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConversationActivity extends AppCompatActivity {

    final List<ConversationItem> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ImageButton sendMessageButton;
    private EditText sendMessageEditText;
    private Toolbar toolbar;

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        String data = getIntent().getExtras().getString("username", "Anonymous");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(data);

        sendMessageButton = (ImageButton) findViewById(R.id.button_send_message);
        sendMessageEditText = (EditText) findViewById(R.id.edit_text_send_message);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendMessageEditText.getText().length() > 0 && sendMessageEditText.getText().toString().trim().length() > 0) {
                    list.add(0, new ConversationItem(sendMessageEditText.getText().toString().trim(), dateFormat.format(date), 0));
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
        list.add(new ConversationItem("вфывфывфывфыафыафывфывыфа фыв фыв фыв фы вфывфывыф",dateFormat.format(date), 0));
        list.add(new ConversationItem("ЛФтвлфывтлфвлыфвы",dateFormat.format(date), 1));
        list.add(new ConversationItem("фывыфаыфафыафыа\n" +
                "ФЫвфывфыаФЫа", dateFormat.format(date), 0));
        list.add(new ConversationItem("ВФЫвыфвфывфыв!", dateFormat.format(date), 1));
        list.add(new ConversationItem("ФЫВфывфывыв —\n" +
                "ВФЫПыфвыфвфывыфв", dateFormat.format(date), 0));
        list.add(new ConversationItem("ФЫВЫФВЫФВФЫаыфаф вфывыфвыфв вфывыфвфыв", dateFormat.format(date), 1));
        list.add(new ConversationItem("йцудйцзхдзмб", dateFormat.format(date), 1));
        list.add(new ConversationItem("мябяючтпшрйзчпзопв", dateFormat.format(date), 1));
        list.add(new ConversationItem("ыфвыфвыфафывыфвыфв", dateFormat.format(date), 0));
        list.add(new ConversationItem("пкуварвар", dateFormat.format(date), 1));
        list.add(new ConversationItem("автавьпзькдж", dateFormat.format(date), 0));
        list.add(new ConversationItem("пькуджп.", dateFormat.format(date), 1));
        list.add(new ConversationItem("аывщьпджьвыжды\n" +
                "Дыватщвыьтаждвы", dateFormat.format(date), 0));
        list.add(new ConversationItem("м сьчсьи чсьбм", dateFormat.format(date), 1));
        return list;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
