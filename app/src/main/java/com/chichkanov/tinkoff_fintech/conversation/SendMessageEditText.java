package com.chichkanov.tinkoff_fintech.conversation;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.chichkanov.tinkoff_fintech.R;

public class SendMessageEditText extends LinearLayout {

    private EditText editText;
    private ImageButton imageButton;

    public SendMessageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    private void initViews(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_message_text, this);
        editText = (EditText) findViewById(R.id.edit_text_send_message);
        imageButton = (ImageButton) findViewById(R.id.button_send_message);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length() > 0){
                    imageButton.setVisibility(VISIBLE);
                }
                else{
                    imageButton.setVisibility(INVISIBLE);
                }
            }
        });
    }

}
