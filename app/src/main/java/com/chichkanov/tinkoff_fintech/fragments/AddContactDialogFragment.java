package com.chichkanov.tinkoff_fintech.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chichkanov.tinkoff_fintech.App;
import com.chichkanov.tinkoff_fintech.DbContract;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.models.DialogsItem;

import java.util.Date;

public class AddContactDialogFragment extends DialogFragment {
    public static final String TAG = "AddContactDialogFragment";
    private OnContentChanged onContentChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogFragmentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Добавить диалог");
        final View v = inflater.inflate(R.layout.dialog_fragment_add_contact, null);
        ImageButton button = (ImageButton) v.findViewById(R.id.ib_add_contact);
        final EditText et = (EditText) v.findViewById(R.id.et_add_contact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().toString().trim().length() > 0) {
                    // TODO check if element already in db
                    SQLiteDatabase writableDatabase = App.getDbhelper().getWritableDatabase();
                    DialogsItem dialogItem = new DialogsItem(et.getText().toString().trim(), null, null);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DbContract.DialogEntry.COLUMN_TITLE, dialogItem.getTitle());
                    contentValues.put(DbContract.DialogEntry.COLUMN_DESCRIPTION, dialogItem.getDesc());
                    contentValues.put(DbContract.DialogEntry.COLUMN_TIMESTAMP, new Date().getTime());
                    writableDatabase.insert(DbContract.DialogEntry.TABLE_NAME, null, contentValues);

                    if (onContentChanged != null) {
                        onContentChanged.itemAdded();
                    }
                    dismissAllowingStateLoss();
                } else {
                    Toast.makeText(getContext(), "Введите логин пользователя", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public void setOnContentChanged(OnContentChanged onContentChanged) {
        this.onContentChanged = onContentChanged;
    }

    public interface OnContentChanged {
        void itemAdded();
    }
}
