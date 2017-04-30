package com.chichkanov.tinkoff_fintech.activities;
import android.content.Context;
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

import com.chichkanov.tinkoff_fintech.ConversationItem;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.adapters.ConversationAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConversationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ConversationItem>> {

    private final static int MESSAGE_LOADER_ID = 0;

    private List<ConversationItem> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ImageButton sendMessageButton;
    private EditText sendMessageEditText;
    private Toolbar toolbar;
    private MessageLoader messageLoader;
    private String userName;

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();

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
                    list.add(0, new ConversationItem(sendMessageEditText.getText().toString().trim(), dateFormat.format(date), 0));
                    adapter.notifyItemInserted(0);
                    sendMessageEditText.setText("");
                    recyclerView.scrollToPosition(0);
                }
            }
        });
        initRecyclerView();
        messageLoader = new MessageLoader(this, userName);
        getSupportLoaderManager().initLoader(MESSAGE_LOADER_ID, null, this);
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_conversation);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    /*private List<ConversationItem> createDataset() {
        list.add(new ConversationItem("1",dateFormat.format(date), 0));
        list.add(new ConversationItem("2",dateFormat.format(date), 1));
        list.add(new ConversationItem("3\n" +
                "3", dateFormat.format(date), 0));
        list.add(new ConversationItem("4!", dateFormat.format(date), 1));
        list.add(new ConversationItem("5 —\n" +
                "5", dateFormat.format(date), 0));
        list.add(new ConversationItem("6 6 6", dateFormat.format(date), 1));
        list.add(new ConversationItem("7", dateFormat.format(date), 1));
        list.add(new ConversationItem("8", dateFormat.format(date), 1));
        list.add(new ConversationItem("999999", dateFormat.format(date), 0));
        list.add(new ConversationItem("10", dateFormat.format(date), 1));
        list.add(new ConversationItem("11", dateFormat.format(date), 0));
        list.add(new ConversationItem("12.", dateFormat.format(date), 1));
        list.add(new ConversationItem("13\n" +
                "13", dateFormat.format(date), 0));
        list.add(new ConversationItem("das", dateFormat.format(date), 1));
        return list;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public Loader<List<ConversationItem>> onCreateLoader(int id, Bundle args) {
        return new MessageLoader(this, userName);
    }

    @Override
    public void onLoadFinished(Loader<List<ConversationItem>> loader, List<ConversationItem> data) {
        this.list.addAll(0, data);
        adapter.notifyItemRangeInserted(0, data.size());
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onLoaderReset(Loader<List<ConversationItem>> loader) {
        adapter.notifyDataSetChanged();
    }

    public static class MessageLoader extends AsyncTaskLoader<List<ConversationItem>>{

        private List<ConversationItem> data;
        private String userName;

        public MessageLoader(Context context, String userName) {
            super(context);
            this.userName = userName;
        }

        @Override
        public List<ConversationItem> loadInBackground() {
            // emulating loading
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            List<ConversationItem> data = new ArrayList<>();

            switch (userName){
                case "Мама":{
                    data.add(new ConversationItem("Сообщение мамы", dateFormat.format(date), 1));
                    data.add(new ConversationItem("Мое сообщение", dateFormat.format(date), 0));
                    data.add(new ConversationItem("Сообщение мамы", dateFormat.format(date), 1));
                    break;
                }
                case "Папа":{
                    data.add(new ConversationItem("Сообщение папы", dateFormat.format(date), 1));
                    data.add(new ConversationItem("Мое сообщение", dateFormat.format(date), 0));
                    data.add(new ConversationItem("Сообщение папы", dateFormat.format(date), 1));
                    break;
                }
                default:{
                    data.add(new ConversationItem("Сообщение noname", dateFormat.format(date), 1));
                    data.add(new ConversationItem("Мое сообщение", dateFormat.format(date), 1));
                    data.add(new ConversationItem("Сообщение noname", dateFormat.format(date), 0));
                    break;
                }
            }


            return data;
        }

        @Override
        public void deliverResult(List<ConversationItem> newData) {
            if(isReset()){
                if(data != null){
                    onReleaseResources(data);
                }
            }

            List<ConversationItem> oldData = data;
            data = newData;

            if(isStarted()){
                super.deliverResult(newData);
            }

            if(oldData != null){
                onReleaseResources(oldData);
            }
        }

        @Override
        protected void onStartLoading() {
            if(data != null){
                deliverResult(data);
            }

            if(takeContentChanged() || data == null){
                forceLoad();
            }

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
            if(data != null){
                onReleaseResources(data);
                data = null;
            }
        }

        private void onReleaseResources(List<ConversationItem> data){

        }
    }
}
