package com.chichkanov.tinkoff_fintech.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chichkanov.tinkoff_fintech.App;
import com.chichkanov.tinkoff_fintech.activities.AddDialogActivity;
import com.chichkanov.tinkoff_fintech.activities.ConversationActivity;
import com.chichkanov.tinkoff_fintech.activities.LoginActivity;
import com.chichkanov.tinkoff_fintech.models.DialogsItem;
import com.chichkanov.tinkoff_fintech.OnItemClickListener;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.adapters.DialogsAdapter;
import com.chichkanov.tinkoff_fintech.presenters.DialogsPresenter;
import com.chichkanov.tinkoff_fintech.views.DialogsView;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogsFragment extends MvpFragment<DialogsView, DialogsPresenter> implements DialogsView {

    private static final int DIALOGS_LOADER_ID = 1;

    private RecyclerView recyclerView;
    private DialogsAdapter adapter;
    private List<DialogsItem> dataset;

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
    public DialogsPresenter createPresenter() {
        return new DialogsPresenter();
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddDialogButtonClick();
            }
        });
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        dataset = createDataset();
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
    public void openDialog(String userId) {

    }

    @Override
    public void showLoading() {
        // Вынести загрузку в отдельынй файл
        new LoginActivity.LoadingDialogFragment().show(getFragmentManager(), LoginActivity.LoadingDialogFragment.TAG);
    }

    @Override
    public void goToAddDialogScreen() {
        Intent intent = new Intent(getActivity(), AddDialogActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String name = data.getStringExtra("name");
        String date = data.getStringExtra("date");
        adapter.addDialog(new DialogsItem(name, "", date));
        adapter.notifyDataSetChanged();
    }
}
