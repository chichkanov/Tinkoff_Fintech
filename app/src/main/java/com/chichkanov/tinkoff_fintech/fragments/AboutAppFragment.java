package com.chichkanov.tinkoff_fintech.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chichkanov.tinkoff_fintech.R;

public class AboutAppFragment extends Fragment {

    private static final String ARG_TITLE = "О приложении";
    private String title;

    public static AboutAppFragment newInstance(String title) {
        AboutAppFragment fragment = new AboutAppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(title);
    }
}
