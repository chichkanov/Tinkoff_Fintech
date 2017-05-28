package com.chichkanov.tinkoff_fintech.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.fragments.LoadingDialogFragment;
import com.chichkanov.tinkoff_fintech.models.DialogsItem;
import com.chichkanov.tinkoff_fintech.presenters.AddDialogPresenter;
import com.chichkanov.tinkoff_fintech.views.AddDialogView;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class AddDialogActivity extends MvpActivity<AddDialogView, AddDialogPresenter> implements AddDialogView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Добавить диалог");

        final EditText userName = (EditText) findViewById(R.id.et_user_name);
        Button addDialog = (Button) findViewById(R.id.btn_add_dialog);
        addDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddDialogButtonClick(userName.getText().toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void addDialog(DialogsItem item) {
        Intent intent = new Intent();
        intent.putExtra("name", item.getTitle());
        intent.putExtra("date", item.getDate());
        setResult(RESULT_OK, intent);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void showLoading() {
        // Вынести загрузку в отдельынй файл
        new LoadingDialogFragment().show(getSupportFragmentManager(), LoadingDialogFragment.TAG);
    }

    @Override
    public AddDialogPresenter createPresenter() {
        return new AddDialogPresenter();
    }
}
