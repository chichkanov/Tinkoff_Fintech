package com.chichkanov.tinkoff_fintech;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DialogsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

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
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
    }

    private void initRecyclerView(View view){
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
        list.add(new DialogsItem("Мама", "Тут последнее сообщение"));
        list.add(new DialogsItem("Папа", "Тут последнее сообщение"));
        return list;
    }

    private void startConversationScreen(String username){
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
