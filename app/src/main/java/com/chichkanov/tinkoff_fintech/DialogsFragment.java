package com.chichkanov.tinkoff_fintech;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<DialogsItem>> {

    private static final int DIALOGS_LOADER_ID = 1;

    private RecyclerView recyclerView;
    private DialogsAdapter adapter;
    public static SQLiteDatabase writableDatabase;
    private DialogsLoader dialogsLoader;
    private LoaderManager.LoaderCallbacks<List<DialogsItem>> callbacks;

    private static final String ARG_TITLE = "Диалоги";
    private String title;

    public static DialogsFragment newInstance(String title) {
        DialogsFragment fragment = new DialogsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_contact);
        callbacks = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddContactDialogFragment dialog = new AddContactDialogFragment();
                dialog.setOnContentChanged(new AddContactDialogFragment.OnContentChanged() {
                    @Override
                    public void itemAdded() {
                        if (dialogsLoader != null) {
                            getActivity().getSupportLoaderManager().restartLoader(DIALOGS_LOADER_ID, null, callbacks);
                        }
                    }
                });
                dialog.show(getActivity().getSupportFragmentManager(), AddContactDialogFragment.TAG);
            }
        });
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
        writableDatabase = App.getDbhelper().getWritableDatabase();
        dialogsLoader = new DialogsLoader(getContext());
        getActivity().getSupportLoaderManager().initLoader(DIALOGS_LOADER_ID, null, this);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final List<DialogsItem> dataset = createDataset();
        adapter = new DialogsAdapter(dataset, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startConversationScreen(dataset.get(position).getTitle());
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private List<DialogsItem> createDataset() {
        List<DialogsItem> list = new ArrayList<>();
        return list;
    }

    private void startConversationScreen(String username) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public Loader<List<DialogsItem>> onCreateLoader(int id, Bundle args) {
        return new DialogsLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<DialogsItem>> loader, List<DialogsItem> data) {
        Log.i("LoaderFinished", "HEKSDA");
        adapter.setItems(data);
    }

    @Override
    public void onLoaderReset(Loader<List<DialogsItem>> loader) {

    }


    public static class DialogsLoader extends AsyncTaskLoader<List<DialogsItem>> {

        private List<DialogsItem> data;

        public DialogsLoader(Context context) {
            super(context);
        }

        @Override
        public List<DialogsItem> loadInBackground() {
            Log.i("LoadInBackground", "Hds");
            List<DialogsItem> data = getPreviousDialogItems();
            Log.i("LoadInBackground", "DatasetSize: " + data.size());
            return data;
        }

        @Override
        public void deliverResult(List<DialogsItem> newData) {
            if (isReset()) {
                if (data != null) {
                    onReleaseResources(data);
                }
            }

            List<DialogsItem> oldData = data;
            data = newData;

            Log.i("DeliverResults", "DatasetSize: " + data.size());

            if (isStarted()) {
                Log.i("DeliverResults STARTED", "DatasetSize: " + data.size());
                super.deliverResult(newData);
            }

            if (oldData != null) {
                onReleaseResources(oldData);
            }
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

        @Override
        public void onCanceled(List<DialogsItem> data) {
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

        private void onReleaseResources(List<DialogsItem> data) {

        }

        @NonNull
        private ArrayList<DialogsItem> getPreviousDialogItems() {
            Cursor cursor = writableDatabase.query(DbContract.DialogEntry.TABLE_NAME,
                    new String[]{
                            DbContract.DialogEntry.COLUMN_TITLE,
                            DbContract.DialogEntry.COLUMN_DESCRIPTION
                    },
                    null,
                    null,
                    null,
                    null,
                    null);
            ArrayList<DialogsItem> dialogItems = new ArrayList<>();
            while (cursor.moveToNext()) {
                int titleIndex = cursor.getColumnIndex(DbContract.DialogEntry.COLUMN_TITLE);
                int descriptionIndex = cursor.getColumnIndex(DbContract.DialogEntry.COLUMN_DESCRIPTION);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                dialogItems.add(new DialogsItem(title, description));
            }
            cursor.close();
            return dialogItems;
        }
    }
}
